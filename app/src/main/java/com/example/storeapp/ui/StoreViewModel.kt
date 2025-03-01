package com.example.storeapp.ui

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeapp.data.LoadingStatus
import com.example.storeapp.data.StoreDatabase
import com.example.storeapp.data.StoreEntity
import com.example.storeapp.data.StoreRepository
import com.example.storeapp.data.TabType
import com.example.storeapp.data.toStoreRecord
import com.example.storeapp.model.MovementRecord
import com.example.storeapp.model.StoreRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date
import java.util.Locale

class StoreViewModel(application: Application) : AndroidViewModel(application) {

    private val storeDatabase = StoreDatabase.getDatabase(application, viewModelScope)
    private val repository = StoreRepository(
        storeDao = storeDatabase.storeDao(),
        movementDao = storeDatabase.movementDao(),
        storeListDao = storeDatabase.storeListDao(),
        userDao = storeDatabase.userDao())

//    val allStores: StateFlow<List<StoreRecord>> = repository.allStores
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = emptyList()
//        )

    private val _uiState = MutableStateFlow(StoreUiState())
    val uiState: StateFlow<StoreUiState> = _uiState

    init {
//        getStoresList()
        getMovementList()
        getAllData()
    }


    fun getAllData(){
        viewModelScope.launch {

            try {
                val listResult = repository.allStores
                Log.i("getAllDataCallData",listResult.first().toString())

                _uiState.update {
                    it.copy(
                        storeDataForMovement = listResult.first()
                    )
                }
            } catch (e: IOException){
                Log.i("Error Provider",e.toString())

                _uiState.update {
                    it.copy(
                    )
                }
            }

        }
    }


    fun updateCurrentTab(tabType: TabType) {
        _uiState.update {
            it.copy(
                currentTab = tabType
            )
        }
    }

    fun getStoreData(currentStoreId: String){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    currentLoadingStatus = LoadingStatus.LOADING
                )
            }

            try {
                val listResult = repository.getStoresByNumber(storeNumber = currentStoreId)
                Log.i("currentStore",listResult.first()[0].toString())
                Log.i("currentStore",listResult.first()[1].toString())


                _uiState.update {
                    it.copy(
                        data = listResult,
                        currentNameSearchList = listResult.first(),
                        currentLoadingStatus = LoadingStatus.SUCCESS
                    )
                }
            } catch (e: Exception){
                Log.i("currentStoreError",e.toString())

                _uiState.update {
                    it.copy(
                        currentLoadingStatus = LoadingStatus.FAILED
                    )
                }
            }

        }
    }

    fun changeExpandStatus(isBoxExpanded: Boolean){
        _uiState.update {
            it.copy(
                isBoxExpanded = !isBoxExpanded
            )
        }
    }

    fun changeMovementExpandStatus(isBoxExpanded: Boolean){
        _uiState.update {
            it.copy(
                isMovementBoxExpanded = !isBoxExpanded
            )
        }
    }
    fun changeBorrowExpandStatus(isBoxExpanded: Boolean){
        _uiState.update {
            it.copy(
                currentRecipientDropExpand = !isBoxExpanded
            )
        }
    }
    fun updateDropBoxTextField(textFieldContent: String){
        _uiState.update {
            it.copy(
                currentSelectedStore = textFieldContent
            )
        }
    }

    fun updateBorrowTextField(textFieldContent: String){
        _uiState.update {
            it.copy(
                currentDialogRecipient = textFieldContent
            )
        }
    }

    fun updateSearchTextField(textFieldContent: String){
        _uiState.update {
            it.copy(
                currentSearchName = textFieldContent
            )
        }
    }


    fun dismissDropBox(){
        _uiState.update {
            it.copy(
                isBoxExpanded = false
            )
        }
    }

    fun dismissMovementDropBox(){
        _uiState.update {
            it.copy(
                isMovementBoxExpanded = false
            )
        }
    }


    fun getMovementList() {
        viewModelScope.launch {


            try {
                val listResult = repository.allMovements
                Log.i("getAllDataCallMove",listResult.first().toString())
                _uiState.update {
                    it.copy(
                        movementsData = listResult.first(),
                        currentLoadingStatus = LoadingStatus.SUCCESS
                    )
                }


            } catch (e: IOException){
                Log.i("currentStoreError",e.toString())

                _uiState.update {
                    it.copy(
                        currentLoadingStatus = LoadingStatus.FAILED
                    )
                }
            }

        }
    }

//    fun getUsersList(){
//        viewModelScope.launch {
//
//
//            try {
//                val usersList = repository.allUsers
//
//                _uiState.update {
//                    it.copy(
//                        usersList = usersList
//                    )
//                }
//            } catch (e: Exception) {
//                _uiState.update {
//                    it.copy(
//                        currentLoadingStatus = LoadingStatus.FAILED
//                    )
//                }
//            }
//        }
//    }



    fun getStoresList(){

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    currentLoadingStatus = LoadingStatus.LOADING
                )
            }

            try {

                _uiState.update {
                    it.copy(
                        stores = listOf("ST01","ST02","ST03","ST04"),
                        currentLoadingStatus = LoadingStatus.SUCCESS
                    )
                }
            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        currentLoadingStatus = LoadingStatus.FAILED
                    )
                }
            }
        }
    }

    fun searchData(currentSearchName: String){

        var searchResult: List<StoreRecord> = listOf()

        viewModelScope.launch {
            searchResult = repository.searchDevicesByName(currentSearchName).map {
                list -> list.map { StoreEntity.toStoreRecord(it)}
            }.first()
        }
        _uiState.update {
            it.copy(
                currentNameSearchList = searchResult
            )
        }
    }

    fun clearSearchTextField(){
        _uiState.update {
            it.copy(
                currentSearchName = ""
            )
        }
    }

    fun scanDataSearch(currentSearchName: String,totalList: Flow<List<StoreRecord>>){

//        val searchQRRecord = (totalList.filter {
//            it.deviceName.contains(currentSearchName,ignoreCase = true) })[0]
//

        val searchQRRecord = (totalList.map { search -> search.filter { it.deviceName == currentSearchName } })
        _uiState.update {
            it.copy(
                currentQRScanRecord = searchQRRecord
            )
        }
    }
    fun updateCardExpand(cardExpandStatus: Boolean){
        _uiState.update {
            it.copy(
                storeCardExpand = !cardExpandStatus
            )
        }

    }

    fun updateDialogShow(DialogShowStatus  : Boolean){
        _uiState.update {
            it.copy(
                isBorrowDropShown = DialogShowStatus
            )
        }

    }
    fun updateCurrentStoreRecord(storeRecord: StoreRecord){
        _uiState.update {
            it.copy(
                currentChosenStoreRecord = storeRecord
            )
        }
    }
    fun addMovement(movementRecord: MovementRecord) {
        viewModelScope.launch {
            try {
                repository.addMovement(movementRecord)
                Log.i("YouClickedBorrow2","")

                // After adding movement, refresh the list
//                repository.refreshMovements()
            } catch (e: Exception) {
                Log.i("YouClickedBorrow3",e.toString())
            }
        }
    }

// For updating device Status After movement "OUT" -> "IN" and vice versa
    fun updateDeviceStatusAfterMovement(storeRecord: StoreRecord, deviceStatus: String) {
        viewModelScope.launch {
            try {
                repository.updateDeviceStatusAfterMovement(
                    deviceName = storeRecord.deviceName,
                    deviceSerialNumber = storeRecord.deviceSerialNumber,
                    deviceStatus = deviceStatus
                )

            } catch (e: Exception) {
                Log.i("Error Provider", e.toString())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateReturnDateAndTimeAfterMovement(storeRecord: StoreRecord) {
        viewModelScope.launch {
            try {
                repository.updateMovementReturnDateAndTime(
                    deviceName = storeRecord.deviceName,
                    deviceSerialNumber = storeRecord.deviceSerialNumber,
                    returnTime = LocalTime.now().toString(),
                    returnDate = LocalDate.now().toString()
                )

            } catch (e: Exception) {
                Log.i("Error Provider", e.toString())
            }
        }
    }


    fun changeConfirmDialogStatus(isExpanded: Boolean){
        _uiState.update {
            it.copy(
                currentConfirmDialogStatus = !isExpanded
            )
        }
    }

    /////////////////////////////////////////////////// This Part For Edit Dialog !!!!!!  ////////////////////////////////////////////////////////////

    // Function to update a field in the edit dialog
    fun updateEditDialogField(fieldName: String, value: String) {
        _uiState.update { currentState ->
            val updatedMap = currentState.editedStoreValues.toMutableMap()
            updatedMap[fieldName] = value
            currentState.copy(editedStoreValues = updatedMap)
        }
    }

    // Function to initialize edit dialog fields with current values
    fun initializeEditDialogFields(storeRecord: StoreRecord) {
        val initialValues = mapOf(
            "deviceName" to storeRecord.deviceName,
            "deviceSerialNumber" to storeRecord.deviceSerialNumber,
            "deviceOfficialName" to storeRecord.deviceOfficialName,
            "deviceOfficialSerial" to storeRecord.deviceOfficialSerial,
            "shelveNumber" to storeRecord.shelveNumber,
            "rackNumber" to storeRecord.rackNumber,
            "storeNumber" to storeRecord.storeNumber,
            "deviceProject" to storeRecord.deviceProject,
            "deviceNotes" to storeRecord.deviceNotes
        )
        
        _uiState.update { it.copy(editedStoreValues = initialValues) }
    }

    // Function to clear edit dialog fields
    fun clearEditDialogFields() {
        _uiState.update { it.copy(editedStoreValues = emptyMap()) }
    }

    fun changeEditDialogExpand(status:Boolean){
        _uiState.update {
            it.copy(
                editDialogExpand = status
            )
        }
    }

    fun editDeviceInformation(storeRecord: StoreRecord){
        viewModelScope.launch {
            // Create updated StoreRecord from the edited values
            val updatedStoreRecord = storeRecord.copy(
                deviceOfficialName = _uiState.value.editedStoreValues["deviceOfficialName"] ?: storeRecord.deviceOfficialName,
                deviceOfficialSerial = _uiState.value.editedStoreValues["deviceOfficialSerial"] ?: storeRecord.deviceOfficialSerial,
                shelveNumber = _uiState.value.editedStoreValues["shelveNumber"] ?: storeRecord.shelveNumber,
                rackNumber = _uiState.value.editedStoreValues["rackNumber"] ?: storeRecord.rackNumber,
                storeNumber = _uiState.value.editedStoreValues["storeNumber"] ?: storeRecord.storeNumber,
                deviceProject = _uiState.value.editedStoreValues["deviceProject"] ?: storeRecord.deviceProject,
                deviceNotes = _uiState.value.editedStoreValues["deviceNotes"] ?: storeRecord.deviceNotes
            )
            
            repository.editDeviceInformation(updatedStoreRecord)

        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////



    //////////////////////////////////////////////////////This Part For Add Dialog !!!!!! /////////////////////////////////////////////////////////



    fun updateAddDialogField(fieldName: String, value: String) {
        _uiState.update { currentState ->
            val currentRecord = currentState.addedStoreValues
            val updatedRecord = when (fieldName) {
                "deviceName" -> currentRecord.copy(deviceName = value)
                "deviceSerialNumber" -> currentRecord.copy(deviceSerialNumber = value)
                "deviceOfficialName" -> currentRecord.copy(deviceOfficialName = value)
                "deviceOfficialSerial" -> currentRecord.copy(deviceOfficialSerial = value)
                "shelveNumber" -> currentRecord.copy(shelveNumber = value)
                "rackNumber" -> currentRecord.copy(rackNumber = value)
                "storeNumber" -> currentRecord.copy(storeNumber = value)
                "deviceProject" -> currentRecord.copy(deviceProject = value)
                "deviceNotes" -> currentRecord.copy(deviceNotes = value)
                else -> currentRecord
            }
            currentState.copy(addedStoreValues = updatedRecord)
        }
    }

    fun clearAddDialogFields() {
        _uiState.update { it.copy(addedStoreValues = StoreRecord()) }
    }

    fun changeAddDialogExpand(status:Boolean){
        _uiState.update {
            it.copy(
                addDialogExpand = status
            )
        }
    }

//    fun addNewDeviceInformation(newDeviceRecord: StoreRecord){
//        viewModelScope.launch {
//            repository.addNewDeviceInformation(newDeviceRecord)
//
//        }
//    }

    fun addNewDevice(device: StoreRecord) {
        viewModelScope.launch {
            try {
                val success = repository.addNewDeviceInformation(device)
                if (!success) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            userMessage = "${device.deviceName} / ${device.deviceSerialNumber} already exists",
                            deviceExistTextExpand = true
                        )
                    }
                } else {
                    _uiState.update { currentState ->
                        currentState.copy(
                            userMessage = "Device with name '${device.deviceName}' and serial number '${device.deviceSerialNumber}' added successfully",
                            deviceExistTextExpand = false,
                            addDialogExpand = false
                        )
                    }
                    if(device.storeNumber.isNotBlank()){
                        _uiState.update { currentState ->
                            currentState.copy(
                                currentSelectedStore = device.storeNumber

                            )
                        }

                            getStoreData(_uiState.value.currentSelectedStore)
                        }
                    clearAddDialogFields()
                }
            } catch (e: IOException) {
                _uiState.update { currentState ->
                    currentState.copy(
                        userMessage = "Failed to add device: ${e.message}",
                        deviceExistTextExpand = true
                    )
                }
            }
        }
    }

    fun updateUserMessage(message: String) {
        _uiState.update { currentState ->
            currentState.copy(userMessage = message)
        }
    }

    fun clearUserMessage() {
        _uiState.update { currentState ->
            currentState.copy(userMessage = "")
        }
    }

    fun changeErrorMessageExpand(expand:Boolean) {
        _uiState.update { currentState ->
            currentState.copy(deviceExistTextExpand = expand)
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // The following function used to fetch data from the server and insert it into the room database
    fun fetchDataFromServerAndInsertItIntoDatabase(){
        viewModelScope.launch {
            val date = Date() // Current date and time
            val formatter = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.ENGLISH)
            val formattedDate = formatter.format(date)
            try {
                repository.fetchDataFromServerAndInsertItIntoDatabase()
                _uiState.update {
                    it.copy(
                        currentLoadingStatus = LoadingStatus.SUCCESS,
                        lastSyncTime = formattedDate

                    )
                }
            }
            catch (e: Exception){
                _uiState.update {
                    it.copy(
                        currentLoadingStatus = LoadingStatus.SUCCESS

                    )
                }

            }            }
    }


}
