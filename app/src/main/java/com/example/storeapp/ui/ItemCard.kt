package com.example.storeapp.ui

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.view.PreviewView
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.storeapp.R

import com.example.storeapp.model.StoreRecord
import com.example.storeapp.ui.theme.StoreAppTheme
import com.example.storeapp.ui.theme.onErrorLight

import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.storeapp.model.MovementRecord
import java.io.File
import android.Manifest
import android.content.pm.PackageManager
import coil.request.ImageRequest

@SuppressLint("RememberReturnType", "SuspiciousIndentation")
@Composable
fun ItemCard(
    viewModel: StoreViewModel,
    storeRecord: StoreRecord,
    modifier: Modifier = Modifier){
    var exapand by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(6.dp))
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = 0.8f,
                    stiffness = Spring.StiffnessLow
                )),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = {exapand = !exapand}
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .sizeIn(minHeight = 64.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(95.dp)
                        .clip(RoundedCornerShape(6.dp))
                ) {
                    val context = LocalContext.current
                    
                    val cameraLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.TakePicture()
                    ) { success ->
                        viewModel.onImageCaptured(success)
                    }

                    val permissionLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission()
                    ) { isGranted ->
                        viewModel.onCameraPermissionResult(isGranted)
                        if (isGranted) {
                            viewModel.prepareImageCapture(context, storeRecord.id)
                            viewModel.cameraState.value.imageUri?.let { uri ->
                                cameraLauncher.launch(uri)
                            }
                        }
                    }

                    Button(
                        onClick = {
                            when (PackageManager.PERMISSION_GRANTED) {
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                ) -> {
                                    viewModel.prepareImageCapture(context, storeRecord.deviceName)
                                    viewModel.cameraState.value.imageUri?.let { uri ->
                                        cameraLauncher.launch(uri)

                                    }
                                }
                                else -> {
                                    permissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(6.dp)),
                        shape = RoundedCornerShape(6.dp),
                    ) {
                        if (storeRecord.deviceImageStatus.isNotEmpty()) {
                            val imageUri = Uri.parse(storeRecord.deviceImageStatus)

                                Box(modifier = Modifier.fillMaxSize()){
                                AsyncImage(
                                    model = imageUri,
                                    contentDescription = "Device Image",
                                    modifier = Modifier.fillMaxSize().sizeIn(100.dp),
                                    contentScale = ContentScale.Crop,

                                )}

                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_background),
                                contentDescription = "Take Picture",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Name",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 1.dp)
                            )
                            Text(text = storeRecord.deviceName)
                        }

                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Rack",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 1.dp)
                            )
                            Text(text = storeRecord.rackNumber)
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 4.dp)
                        ) {
                            Text(
                                text = "Shelf",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 1.dp)
                            )
                            Text(text = storeRecord.shelveNumber)
                        }


                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 4.dp)
                        ) {
                            Text(
                                text = "Status",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 1.dp)
                            )
                            Text(text = storeRecord.deviceStatus)
                        }
                    }
                }
            }
            if(exapand){
                ItemCardDetail(viewModel = viewModel,storeRecord = storeRecord)

            }
        }

    }
}


@Composable
fun ItemCardDetail(
    viewModel: StoreViewModel,
    storeRecord: StoreRecord){
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column(

                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
            ) {

                Text(
                    text = "Store",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 1.dp)
                )
                Text(text = storeRecord.storeNumber)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
            ) {

                Text(
                    text = "S/N",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 1.dp)
                )
                Text(text = storeRecord.deviceSerialNumber)
            }


            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
            ) {

                Text(
                    text = "Project",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 1.dp)
                )
                Text(text = storeRecord.deviceProject)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterHorizontally)
        ) {
            val buttonDisable: Boolean
            if(storeRecord.deviceStatus == "OUT"){
                buttonDisable = true
            }else {
                buttonDisable = false

            }
            Button(onClick = {
                viewModel.updateDialogShow(true)
                viewModel.updateCurrentStoreRecord(storeRecord)
            }, enabled = !buttonDisable, shape = RoundedCornerShape(6.dp) ,modifier = Modifier.width(100.dp)) { Text("Borrow") }


            Button(onClick = {
                viewModel.changeConfirmDialogStatus(false)
                viewModel.updateCurrentStoreRecord(storeRecord)

            }, enabled = buttonDisable,shape = RoundedCornerShape(6.dp) ,modifier = Modifier.width(100.dp)) { Text("Return") }



            Button(onClick = {
                viewModel.changeEditDialogExpand(true)

                viewModel.updateCurrentStoreRecord(storeRecord)

            },shape = RoundedCornerShape(6.dp) ,modifier = Modifier.width(100.dp)) { Text("Edit") }


        }
    }


}
