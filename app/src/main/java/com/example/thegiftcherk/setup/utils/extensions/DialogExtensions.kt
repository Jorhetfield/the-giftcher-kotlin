package com.example.thegiftcherk.setup.utils.extensions

import com.example.thegiftcherk.setup.BaseActivity

private enum class States(val states: Int?) {
    NOT_ASSIGNED(1),
    ASSIGNED(2),
    ON_ROUTE(3),
    FINISHED(4),
}

//fun BaseActivity.showProgressDialog() {
//
//    hideProgressDialog()
//    Dialog(this).apply {
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        setCancelable(false)
//        setContentView(R.layout.dialog_progress)
//        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        show()
//        this@showProgressDialog.progressDialog = this
//    }
//}


//fun BaseActivity.showNewService(route: ServiceResponse?) {
//
//    hideProgressDialog()
//    Dialog(this).apply {
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        setCancelable(false)
//        setContentView(R.layout.dialog_progress)
//        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        show()
//    }
//}


fun BaseActivity.hideProgressDialog() {
    this.progressDialog?.dismiss()
}

//fun BaseActivity.showFares() {
//    val vanadisRepository by inject<VanadisRepository>()
//    Dialog(this).apply {
//
//
//        fun changeState() {
//
//            GlobalScope.launch(Dispatchers.Main) {
//                showProgressDialog()
//                when (val response = vanadisRepository.changeState(
//                    prefs.token.toString()
//                )) {
//                    is ResponseResult.Success -> {
//
//                        logD(" respuesta cambio de estado ${response.value}")
//                    }
//                    is ResponseResult.NotContent -> {
//                        hideProgressDialog()
//                        showError(response.message)
//                        logD(" respuesta cambio de estado $response")
//
//                    }
//                    is ResponseResult.Error -> {
//                        hideProgressDialog()
//                        showError(response.message)
//                        logD(" respuesta cambio de estado $response")
//
//                    }
//                    is ResponseResult.Forbidden -> {
//                        hideProgressDialog()
//                        showError(response.message)
//                        logD(" respuesta cambio de estado $response")
//
//                    }
////                else -> showError("error")
//                }
//                hideProgressDialog()
//            }
//
//            //TODO hacer que la petición envíe el estado y la tarifa
//        }
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        setCancelable(false)
//        setContentView(R.layout.fares_dialog)
//        radioGroup.check(R.id.radioButton)
//        changeFareBtn.setOnClickListener {
//            changeState()
//            this.dismiss()
//        }
//        show()
//
//
//    }
//}
//
//fun BaseActivity.showCancelled() {
//
//    fun onServiceCancelled() {
//        val navController = findNavController(R.id.fragment)
//        navController.navigate(R.id.filterFragment)
//
//    }
//    Dialog(this).apply {
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        setCancelable(false)
//        setContentView(R.layout.cancel_dialog)
//
//        changeFareBtn.setOnClickListener {
//            onServiceCancelled()
//            this.dismiss()
//        }
//        show()
//    }
//
//}
//
//fun BaseActivity.showTaximetro() {
//
//    val vanadisRepository by inject<VanadisRepository>()
//
//
//    fun assignService(state: Int?, serviceId: String?, priceTaximeter: Float) {
//        GlobalScope.launch(Dispatchers.Main) {
//            showProgressDialog()
//            when (val response =
//                vanadisRepository.assignService(state, serviceId, priceTaximeter)) {
//                is ResponseResult.Success -> {
//                    val respuesta = response.value
//                    logDD(" hola hola $respuesta")
////                    showMessage("has aceptado el viaje", linearContainer)
//                    logDD("Dialogo Estado $state")
//                    logDD("Dialogo Estado $serviceId")
//                    logDD("Dialogo Estado $priceTaximeter")
//
//                }
//                is ResponseResult.Error -> {
////                    showError("${response.message} ", linearContainer)
//
//                }
//                is ResponseResult.Forbidden -> {
////                    showError(response.message, linearContainer)
//                }
//            }
//            hideProgressDialog()
//        }
//    }
//    Dialog(this).apply {
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        setCancelable(false)
//        setContentView(R.layout.taximetro_dialog)
//        sendTaximetro.setOnClickListener {
//            if (!taximetro.text.isNullOrEmpty()){
//                val taximetro = taximetro.text.toString()
//                val number = taximetro.toFloat()
//                logDD("Dialogo Estado ${prefs.serviceId}")
//                logDD("Dialogo Estado ${States.FINISHED.states}")
//                logDD("Dialogo Estado $number")
//
//                assignService(States.FINISHED.states, prefs.serviceId.toString(), number)
//
//                this.dismiss()
//            } else {
//                toastLong("El taximetro no puede estar vacío")
//            }
//
//        }
//        show()
//    }






