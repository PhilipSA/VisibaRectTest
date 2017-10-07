package com.example.visiba.visibarectest.Fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraMetadata
import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.TotalCaptureResult
import android.media.ImageReader
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.Log
import android.util.Size
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast

import java.util.ArrayList
import java.util.Arrays

import com.example.papersoccer.visibarectest.R
import com.example.visiba.visibarectest.Activities.CameraActivity
import com.example.visiba.visibarectest.AppImage
import com.example.visiba.visibarectest.Enums.CameraTypeEnum
import com.example.visiba.visibarectest.Fragments.Abstractions.IResultReturning
import com.example.visiba.visibarectest.Repositories.AppImageRepository

class CameraPreviewFragment : Fragment(), IResultReturning<AppImage> {

    private var takePictureButton: ImageButton? = null
    private var exitCameraImageButton: ImageButton? = null
    private var swapCameraImageButton: ImageButton? = null
    private var currentCamera = 0

    private var textureView: TextureView? = null
    private var cameraId: String? = null
    protected var cameraDevice: CameraDevice? = null
    lateinit protected var cameraCaptureSessions: CameraCaptureSession
    lateinit protected var captureRequestBuilder: CaptureRequest.Builder
    private var imageDimension: Size? = null
    private var imageReader: ImageReader? = null
    private var appImage: AppImage? = null
    private var mBackgroundHandler: Handler? = null
    lateinit private var appImageRepository: AppImageRepository

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_camera_preview, container, false)
        super.onCreate(savedInstanceState)
        textureView = view.findViewById<TextureView>(R.id.cameraPreviewSurfaceView)
        textureView!!.surfaceTextureListener = textureListener

        takePictureButton = view.findViewById<ImageButton>(R.id.takePhotoImageButton)
        takePictureButton!!.setOnClickListener { takePicture() }

        swapCameraImageButton = view.findViewById<ImageButton>(R.id.swapCameraImageButton)
        swapCameraImageButton!!.setOnClickListener { swapCamera() }

        appImageRepository = AppImageRepository()

        exitCameraImageButton = view.findViewById<ImageButton>(R.id.exitCameraImageButton)
        exitCameraImageButton!!.setOnClickListener { finish(null) }
        return view
    }

    override fun finish(result: AppImage?) = if (result != null) {
        (activity as CameraActivity).finish(result)
    } else {
        activity.finish()
    }

    private fun swapCamera() {
        currentCamera = if (currentCamera == CameraTypeEnum.BACK) CameraTypeEnum.FRONT else CameraTypeEnum.BACK
        closeCamera()
        openCamera(currentCamera)
    }

    internal var textureListener: TextureView.SurfaceTextureListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
            openCamera(currentCamera)
        }
        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
            // Transform you image captured size according to the surface width and height
        }
        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean = false
        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
    }

    private val stateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            //This is called when the camera is open
            Log.e(TAG, "onOpened")
            cameraDevice = camera
            createCameraPreview()
        }

        override fun onDisconnected(camera: CameraDevice) {
            cameraDevice!!.close()
        }

        override fun onError(camera: CameraDevice, error: Int) {
            cameraDevice!!.close()
            cameraDevice = null
        }
    }

    private fun takePicture() {
        if (null == cameraDevice) {
            Log.e(TAG, "cameraDevice is null")
            return
        }
        val manager = activity.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            val characteristics = manager.getCameraCharacteristics(cameraDevice!!.id)
            var jpegSizes: Array<Size>? = null
            if (characteristics != null) {
                jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)!!.getOutputSizes(ImageFormat.JPEG)
            }
            var width = 640
            var height = 480
            if (jpegSizes != null && 0 < jpegSizes.size) {
                width = jpegSizes[0].width
                height = jpegSizes[0].height
            }
            val reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1)
            val outputSurfaces = ArrayList<Surface>(2)
            outputSurfaces.add(reader.surface)
            outputSurfaces.add(Surface(textureView!!.surfaceTexture))
            val captureBuilder = cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            captureBuilder.addTarget(reader.surface)
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)

            val rotation = activity.windowManager.defaultDisplay.rotation
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation))
            val readerListener = ImageReader.OnImageAvailableListener { reader ->
                try {
                    appImage = AppImage(reader.acquireLatestImage())
                    appImageRepository.saveAppImage(appImage!!)
                } finally {
                    appImage?.let {
                        finish(it)
                    }
                }
            }
            reader.setOnImageAvailableListener(readerListener, mBackgroundHandler)
            val captureListener = object : CameraCaptureSession.CaptureCallback() {
                override fun onCaptureCompleted(session: CameraCaptureSession, request: CaptureRequest, result: TotalCaptureResult) {
                    super.onCaptureCompleted(session, request, result)
                    Toast.makeText(activity, "Saved:" + appImage!!.imageId, Toast.LENGTH_SHORT).show()
                    createCameraPreview()
                }
            }
            cameraDevice!!.createCaptureSession(outputSurfaces, object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(session: CameraCaptureSession) {
                    try {
                        session.capture(captureBuilder.build(), captureListener, mBackgroundHandler)
                    } catch (e: CameraAccessException) {
                        e.printStackTrace()
                    }

                }

                override fun onConfigureFailed(session: CameraCaptureSession) {}
            }, mBackgroundHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

    }

    private fun createCameraPreview() = try {
        val texture = textureView!!.surfaceTexture!!
        texture.setDefaultBufferSize(imageDimension!!.width, imageDimension!!.height)
        val surface = Surface(texture)
        captureRequestBuilder = cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        captureRequestBuilder.addTarget(surface)
        cameraDevice!!.createCaptureSession(Arrays.asList(surface), object : CameraCaptureSession.StateCallback() {
            override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                if (null == cameraDevice) {
                    return
                }

                cameraCaptureSessions = cameraCaptureSession
                updatePreview()
            }

            override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession) {
                Toast.makeText(activity, "Configuration change", Toast.LENGTH_SHORT).show()
            }
        }, null)
    } catch (e: CameraAccessException) {
        e.printStackTrace()
    }

    private fun openCamera(cameraTypeEnum: Int) {
        val manager = activity.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        Log.e(TAG, "is camera open")
        try {
            cameraId = manager.cameraIdList[cameraTypeEnum]
            val characteristics = manager.getCameraCharacteristics(cameraId!!)
            val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
            imageDimension = map!!.getOutputSizes<SurfaceTexture>(SurfaceTexture::class.java)[0]

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CAMERA_PERMISSION)
                return
            }
            manager.openCamera(cameraId!!, stateCallback, null)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

        Log.e(TAG, "openCamera X")
    }

    private fun updatePreview() {
        if (null == cameraDevice) {
            Log.e(TAG, "updatePreview error, return")
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
        try {
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), null, mBackgroundHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

    }

    private fun closeCamera() {
        if (null != cameraDevice) {
            cameraDevice!!.close()
            cameraDevice = null
        }
        if (null != imageReader) {
            imageReader!!.close()
            imageReader = null
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // close the app
                Toast.makeText(activity, R.string.camera_permission_denied, Toast.LENGTH_LONG).show()
                activity.finish()
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera(currentCamera)
            }
        }
    }

    companion object {

        private val TAG = "AndroidCameraApi"
        private val ORIENTATIONS = SparseIntArray()

        init {
            ORIENTATIONS.append(Surface.ROTATION_0, 90)
            ORIENTATIONS.append(Surface.ROTATION_90, 0)
            ORIENTATIONS.append(Surface.ROTATION_180, 270)
            ORIENTATIONS.append(Surface.ROTATION_270, 180)
        }

        private val REQUEST_CAMERA_PERMISSION = 200
    }
}
