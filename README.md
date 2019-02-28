# Engorgio-Bottom-Bar
A bottom bar with an expanding title effect for android


![alt text](https://raw.githubusercontent.com/AkashBang/Engorgio-Bottom-Bar/master/images/demo_gif.gif)



- Written In Kotlin
- Handle 5 Tabs At Max
- Light weight




**Dependency**

Project Level

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}


App Level

	dependencies {
		implementation 'com.github.AkashBang:Engorgio-Bottom-Bar:0.1.0'
		}


**Code Example**

XML

	<com.engorgio.engorgiobottombar.EngorgioBottomBar
            android:background="#FFF"
            android:layout_gravity="bottom"
            android:id="@+id/engorgioBar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>


Kotlin

	class MainActivity : AppCompatActivity() {
    	override fun onCreate(savedInstanceState: Bundle?) {
			super.onCreate(savedInstanceState)
			setContentView(R.layout.activity_main)
			val tab1 = ETab(R.drawable.house, "Home",Color.parseColor("#b69bfc"))
			val tab2 = ETab(R.drawable.heart, "Heart", Color.parseColor("#f963d1"))
			val tab3 = ETab(R.drawable.search, "Search",Color.parseColor("#fcbd4c"))
			val tab4 = ETab(R.drawable.avatar, "User", Color.parseColor("#4adcfd"))
			val tablist = arrayListOf(tab1, tab2, tab3, tab4)


			// You can optionally set extras to tabs which you can retrieve back on the tab clicked callback 
			val extra = Bundle()
			extra.putString("Key","Value")
			tab1.extra = extra


			engorgioBar.setUserTabs(tablist)
			engorgioBar.setOnTabClickListener(object :EngorgioBottomBar.OnTabClickListener{

				override fun onTabClicked(position: Int, eTab: ETab) {
					//Handle Tab Click Here
				}
			})
		}
	}


