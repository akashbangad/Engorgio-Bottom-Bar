package com.engorgio.engorgiobottombar

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class EngorgioBottomBar : ConstraintLayout, View.OnClickListener {

    private val titleViews = ArrayList<TextView>()
    private val iconViews = ArrayList<ImageView>()
    private val backgroundViews = ArrayList<LinearLayout>()

    private var userTabs = ArrayList<ETab>()
    private var onTabClickListener: OnTabClickListener? = null

    companion object {
        const val MAXIMUM_TAB_LIMIT = 5
    }

    constructor(context: Context) : super(context) {
        setUpView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setUpView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setUpView()
    }


    fun setUserTabs(tabs: ArrayList<ETab>) {
        if (tabs.size < MAXIMUM_TAB_LIMIT) {
            userTabs = tabs
            userTabs.forEachIndexed { index, eTab ->
                backgroundViews[index].visibility = View.VISIBLE
                iconViews[index].setImageResource(eTab.iconRes)
                iconViews[index].setColorFilter(Color.DKGRAY, android.graphics.PorterDuff.Mode.SRC_IN)
                backgroundViews[index].setOnClickListener(this@EngorgioBottomBar)
            }
        }else{
            throw Throwable("Maximum allowed tabs are limited to 5, please make sure you are setting 5 tabs")
        }
    }

    private fun setUpView() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val root = inflater.inflate(R.layout.engorgio_bottom_bar, this, true)

        val tab1 = root.findViewById<LinearLayout>(R.id.tab_1)
        val tab2 = root.findViewById<LinearLayout>(R.id.tab_2)
        val tab3 = root.findViewById<LinearLayout>(R.id.tab_3)
        val tab4 = root.findViewById<LinearLayout>(R.id.tab_4)
        val tab5 = root.findViewById<LinearLayout>(R.id.tab_5)

        val title1 = root.findViewById<TextView>(R.id.tab_1_title)
        val title2 = root.findViewById<TextView>(R.id.tab_2_title)
        val title3 = root.findViewById<TextView>(R.id.tab_3_title)
        val title4 = root.findViewById<TextView>(R.id.tab_4_title)
        val title5 = root.findViewById<TextView>(R.id.tab_5_title)

        val icon1 = root.findViewById<ImageView>(R.id.tab_1_icon)
        val icon2 = root.findViewById<ImageView>(R.id.tab_2_icon)
        val icon3 = root.findViewById<ImageView>(R.id.tab_3_icon)
        val icon4 = root.findViewById<ImageView>(R.id.tab_4_icon)
        val icon5 = root.findViewById<ImageView>(R.id.tab_5_icon)

        titleViews.add(title1)
        titleViews.add(title2)
        titleViews.add(title3)
        titleViews.add(title4)
        titleViews.add(title5)

        iconViews.add(icon1)
        iconViews.add(icon2)
        iconViews.add(icon3)
        iconViews.add(icon4)
        iconViews.add(icon5)

        backgroundViews.add(tab1)
        backgroundViews.add(tab2)
        backgroundViews.add(tab3)
        backgroundViews.add(tab4)
        backgroundViews.add(tab5)


    }

    fun setOnTabClickListener(onTabClickListener: OnTabClickListener) {
        this.onTabClickListener = onTabClickListener
    }

    override fun onClick(v: View?) {
        var index = 0
        when (v?.id) {
            R.id.tab_1 -> index = 0
            R.id.tab_2 -> index = 1
            R.id.tab_3 -> index = 2
            R.id.tab_4 -> index = 3
            R.id.tab_5 -> index = 4
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(this@EngorgioBottomBar)
        }

        titleViews[index].text = userTabs[index].title
        titleViews[index].setTextColor(Color.WHITE)

        iconViews[index].setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN)

        val shapeDrawable = ContextCompat.getDrawable(context, R.drawable.rounded_rectangle_bg) as GradientDrawable
        shapeDrawable.setColor(userTabs[index].backgroundColorArgb)
        backgroundViews[index].background = shapeDrawable

        onTabClickListener?.onTabClicked(index, userTabs[index])

        for (i in 0 until userTabs.size) {
            if (i != index) {
                titleViews[i].setTextColor(Color.DKGRAY)
                iconViews[i].setColorFilter(Color.DKGRAY, android.graphics.PorterDuff.Mode.SRC_IN)
                backgroundViews[i].background = null
                titleViews[i].text = ""
            }
        }


    }


    interface OnTabClickListener {
        fun onTabClicked(position: Int, eTab: ETab)
    }
}


