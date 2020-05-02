package com.example.thegiftcherk.features.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.main.MainLoginActivity
import com.example.thegiftcherk.features.ui.tutorial.TutorialActivity
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.network.ResponseResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_help.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HelpFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_help, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        watchTutorial?.setOnClickListener {
            startTutorialActivity()
        }

        FAQButton?.setOnClickListener {
            openWeb("http://thegiftcher.com")
        }

        contactUsButton?.setOnClickListener {
            val action = HelpFragmentDirections.actionHelpFragmentToContactUsFragment()
            Navigation.findNavController(view).navigate(action)
        }

        deleteAccountButton?.setOnClickListener {
            MaterialAlertDialogBuilder(context, R.style.DialogTheme1)
                .setTitle("¿Quieres borrar tu cuenta para siempre?")
                .setNegativeButton("No", null)
                .setPositiveButton("Si") { _, _ ->
                    MaterialAlertDialogBuilder(context, R.style.DialogTheme1)
                        .setTitle("¿Lo estás diciendo en serio?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Si") { _, _ ->
                            MaterialAlertDialogBuilder(context, R.style.DialogTheme1)
                                .setTitle("¿Seguro que si?")
                                .setNegativeButton("No", null)
                                .setPositiveButton("Que si") { _, _ ->
                                    MaterialAlertDialogBuilder(context, R.style.DialogTheme1)
                                        .setTitle("Tranqui tío, solo quiero asegurarme")
                                        .setNegativeButton("Quiero irme",null)
                                        .setPositiveButton("ESTOY TRANQUILO!") { _, _ ->
                                            MaterialAlertDialogBuilder(context, R.style.DialogTheme1)
                                                .setTitle("Allévoy!")
                                                .setNegativeButton("ADEU") { _, _ ->
                                                    deleteAccount()

                                                }
                                                .setPositiveButton("AGUR") { _, _ ->
                                                    deleteAccount()

                                                }.show()
                                        }.show()

                                }.show()

                        }.show()

                }.show()
        }
    }

    private fun deleteAccount() {
        GlobalScope.launch(Dispatchers.Main) {
            showProgressDialog()
            when (val response =
                customRepository.deleteAccount()) {
                is ResponseResult.Success -> {
                    prefs.clear()
                    startLoginActivity()
                    showMessage("Borrado correctamente", constraintHelpContainer)

                }
                is ResponseResult.Error -> {
                    showError(
                        "Ha habido un error con la petición ${response.message}",
                        constraintHelpContainer
                    )
                }
                is ResponseResult.Forbidden -> {
                    showError(
                        "Ha habido un error con la petición ${response.message}",
                        constraintHelpContainer
                    )

                }
            }
            hideProgressDialog()
        }
    }

    private fun openWeb(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    private fun startTutorialActivity() {
        context?.let {
            val intent = TutorialActivity.intent(it)
            startActivity(intent)
        }
    }

    private fun startLoginActivity() {
        context?.let {
            val intent = MainLoginActivity.intent(it)
            startActivity(intent)
            activity?.finish()
        }
    }

}