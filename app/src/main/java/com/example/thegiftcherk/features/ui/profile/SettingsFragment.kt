package com.example.thegiftcherk.features.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.main.MainLoginActivity
import com.example.thegiftcherk.setup.BaseFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editProfileButton?.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToEditProfileFragment()
            Navigation.findNavController(view).navigate(action)
        }

        termsAndConditionsButton?.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToTextContainerFragment(0)
            Navigation.findNavController(view).navigate(action)
        }

        privacyPoliticButton?.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToTextContainerFragment(1)
            Navigation.findNavController(view).navigate(action)
        }

        helpButton?.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToHelpFragment()
            Navigation.findNavController(view).navigate(action)
        }

        logoutButton?.setOnClickListener {
            MaterialAlertDialogBuilder(context, R.style.DialogTheme1)
                .setTitle("¿De verdad quieres cerrar sesión?")
                .setNegativeButton("No", null)
                .setPositiveButton("Si") { _, _ ->
                    prefs.clear()
                    startLoginActivity()
                }.show()
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