package com.example.thegiftcherk.features.ui.tutorial

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.carousel.ImagesAdapter
import com.example.thegiftcherk.features.ui.carousel.indicators.LinePagerIndicatorDecoration
import com.example.thegiftcherk.features.ui.main.MainActivity
import com.example.thegiftcherk.setup.BaseActivity
import es.vanadis.estaxitablet.features.carousel.extensions.color
import es.vanadis.estaxitablet.features.carousel.extensions.dp
import kotlinx.android.synthetic.main.activity_tutorial.*


class TutorialActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        homeButton?.setOnClickListener {
//            startMainActivity()
            fragmentLikes()
            prefs.firstLogin = false
        }

        val pictureUrlList: MutableList<Pair<Drawable, String>> = mutableListOf()

        pictureUrlList.add(Pair(resources.getDrawable(R.drawable.lista_gustos),"Explora listas relacionadas con tus gustos"))
        pictureUrlList.add(Pair(resources.getDrawable(R.drawable.aniadir_1),"Añade deseos a tu lista personalizada"))
        pictureUrlList.add(Pair(resources.getDrawable(R.drawable.aniadir_2),"Elige entre las categorías"))
        pictureUrlList.add(Pair(resources.getDrawable(R.drawable.lista_deseos),"Explora tu lista de deseos"))
        pictureUrlList.add(Pair(resources.getDrawable(R.drawable.detalle_deseo),"Mira los detalles de tus deseos"))
        pictureUrlList.add(Pair(resources.getDrawable(R.drawable.lista_amigos),"Explora entre tus amigos y añade más"))
        pictureUrlList.add(Pair(resources.getDrawable(R.drawable.detalle_amigo),"Cotillea las listas de tus amigos"))
        pictureUrlList.add(Pair(resources.getDrawable(R.drawable.perfil),"Edita tu perfil"))
        pictureUrlList.add(Pair(resources.getDrawable(R.drawable.perfil_reservas),"Contempla los regalos que tienes asignados"))


        tutorialViewPager.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.color_detail
            )
        )

        tutorialViewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position == 0){
                    backward?.visibility = View.INVISIBLE
                } else {
                    backward?.visibility = View.VISIBLE
                }
                if (position == pictureUrlList.lastIndex){
                    homeButton?.visibility = View.VISIBLE
                    forward?.visibility = View.INVISIBLE
                } else {
                    homeButton?.visibility = View.INVISIBLE
                    forward?.visibility = View.VISIBLE
                }

                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })

        forward?.setOnClickListener {
            tutorialViewPager?.setCurrentItem(tutorialViewPager?.currentItem!!.plus(1))
        }

        backward?.setOnClickListener {
            tutorialViewPager?.setCurrentItem(tutorialViewPager?.currentItem!!.minus(1))
        }

        tutorialViewPager.apply {
            // ListAdapter has a method, submitList, to add the list
            adapter = ImagesAdapter().apply { submitList(pictureUrlList) }

            // LinePagerIndicatorDecoration has default values (#ff000000, 60000000, and 0f respectively)
            val pagerDecoration = LinePagerIndicatorDecoration(
                colorActive = context.color(R.color.colorDetailDark),
                colorInactive = context.color(R.color.white80Transparent),
                offSet = 0f.dp // let us to overlay the image with the page indicators (24f.dp = indicators move up 24 dpi)
            )
            addItemDecoration(pagerDecoration)
        }
    }

    private fun fragmentLikes(){
        val fragment = LikesFragment()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    private fun startMainActivity() {
        this.let {
            val intent = MainActivity.intent(it)
            startActivity(intent)
            this.finish()
        }
    }

    companion object {
        private val LOGTAG: String = MainActivity::class.java.simpleName

        @JvmStatic
        fun intent(context: Context) =
            Intent(context, TutorialActivity::class.java)
    }
}
