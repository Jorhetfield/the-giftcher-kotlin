package com.example.thegiftcherk.features.ui.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.main.MainActivity
import com.example.thegiftcherk.setup.BaseFragment
import com.example.thegiftcherk.setup.utils.extensions.json
import com.example.thegiftcherk.setup.utils.extensions.logD
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_likes.*


class LikesFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_likes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val checkedChips: MutableList<String> = mutableListOf()

        chipGroup.forEach { view ->
            view.setOnClickListener { chip ->
                logD("check ${(chip as Chip).text} esta ${(chip).isChecked}")

                if (chip.isChecked && !checkedChips.contains(chip.text.toString())) {

                    checkedChips.add(chip.text.toString())

                } else if (!chip.isChecked && checkedChips.contains(chip.text.toString())) {

                    checkedChips.remove(chip.text.toString())

                }

                buttonSendLikes?.setOnClickListener {

                    if (checkedChips.size < 4) {
                        showError(
                            "Tienes que marcar, al menos, 4 gustos.",
                            constraintLikesContainer
                        )
                    } else {
                        prefs.likes = checkedChips.json()
                        logD("checked chips $checkedChips")
                        startMainActivity()
                    }


                }

            }
            logD("$view")
        }
    }

    private fun startMainActivity() {
        context?.let {
            prefs.firstLogin = false
            val intent = MainActivity.intent(it)
            startActivity(intent)
            activity?.finish()
        }
    }

}