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
import android.widget.TextView

class EngorgioBottomBar : ConstraintLayout, View.OnClickListener {

    private val tabViews = ArrayList<TextView>()
    private var userTabs = ArrayList<ETab>()
    private var onTabClickListener: OnTabClickListener? = null

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
        userTabs = tabs
        userTabs.forEachIndexed { index, eTab ->
            tabViews[index].setCompoundDrawablesWithIntrinsicBounds(eTab.iconRes, 0, 0, 0)
            tabViews[index].compoundDrawables[0].setColorFilter(Color.DKGRAY, android.graphics.PorterDuff.Mode.SRC_IN)
            tabViews[index].visibility = View.VISIBLE
            tabViews[index].setOnClickListener(this@EngorgioBottomBar)
        }
    }

    private fun setUpView() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val root = inflater.inflate(R.layout.engorgio_bottom_bar, this, true)
        val tab1 = root.findViewById<TextView>(R.id.tab_1)
        val tab2 = root.findViewById<TextView>(R.id.tab_2)
        val tab3 = root.findViewById<TextView>(R.id.tab_3)
        val tab4 = root.findViewById<TextView>(R.id.tab_4)
        val tab5 = root.findViewById<TextView>(R.id.tab_5)

        tabViews.add(tab1)
        tabViews.add(tab2)
        tabViews.add(tab3)
        tabViews.add(tab4)
        tabViews.add(tab5)


    }

    fun setOnTabClickListener(onTabClickListener: OnTabClickListener){
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

        tabViews[index].text = userTabs[index].title
        tabViews[index].setTextColor(Color.WHITE)
        tabViews[index].compoundDrawables[0].setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN)
        val shapeDrawable = ContextCompat.getDrawable(context, R.drawable.rounded_rectangle_bg) as GradientDrawable
        shapeDrawable.setColor(userTabs[index].backgroundColorArgb)
        tabViews[index].background = shapeDrawable
        onTabClickListener?.onTabClicked(index, userTabs[index])

        for (i in 0 until userTabs.size ) {
            if (i != index) {
                tabViews[i].setTextColor(Color.DKGRAY)
                tabViews[i].compoundDrawables[0].setColorFilter(Color.DKGRAY, android.graphics.PorterDuff.Mode.SRC_IN)
                tabViews[i].background = null
                tabViews[i].text = ""
            }
        }


    }


    interface OnTabClickListener {
        fun onTabClicked(position: Int, eTab: ETab)
    }
}