package com.example.petmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.petmate.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager

    private lateinit var binding : ActivityMainBinding

    private var fragmentOneTest: FragmentOneTest? = null
    private var fragmentTwoTest: FragmentTwoTest? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        initBottomNavigation()


//        setContentView(R.layout.activity_main_home_havepet)
//
//        val rv_board_petlist = findViewById<ViewPager2>(R.id.havepet_petlist_viewpager)
//        val rv_board_schedule = findViewById<RecyclerView>(R.id.havepet_schedule)
//        val rv_board_weather = findViewById<RecyclerView>(R.id.havepet_weather)
//
//        val PetList = ArrayList<MainHomeHavepetPetlistData>()
//        val ScheduleList = ArrayList<MainHomeHavepetScheduleData>()
//        val WeatherList = ArrayList<MainHomeHavepetWeatherData>()
//
//        PetList.add(MainHomeHavepetPetlistData("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
//        PetList.add(MainHomeHavepetPetlistData("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
//        PetList.add(MainHomeHavepetPetlistData("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
//
//        ScheduleList.add(MainHomeHavepetScheduleData("08:00 am", "MainText", "SubText"))
//        ScheduleList.add(MainHomeHavepetScheduleData("08:00 am", "MainText", "SubText"))
//        ScheduleList.add(MainHomeHavepetScheduleData("08:00 am", "MainText", "SubText"))
//
//        WeatherList.add(MainHomeHavepetWeatherData("28℃", "17시"))
//        WeatherList.add(MainHomeHavepetWeatherData("27℃", "18시"))
//        WeatherList.add(MainHomeHavepetWeatherData("26℃", "19시"))
//
//        val boardAdapterPetList = MainHomeHavepetPetlist(PetList)
//        boardAdapterPetList.notifyDataSetChanged()
//        val boardAdapterScheduleList = MainHomeHavepetSchedule(ScheduleList)
//        boardAdapterScheduleList.notifyDataSetChanged()
//        val boardAdapterWeatherList = MainHomeHavepetWeather(WeatherList)
//        boardAdapterWeatherList.notifyDataSetChanged()
//
////        rv_board_petlist.adapter = boardAdapterPetList
//        rv_board_petlist.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//
//        rv_board_schedule.adapter = boardAdapterScheduleList
//        rv_board_schedule.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//
//        rv_board_weather.adapter = boardAdapterWeatherList
//        rv_board_weather.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
    private fun initBottomNavigation(){
        // 최초로 보이는 프래그먼트
        fragmentOneTest = FragmentOneTest()
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView,fragmentOneTest!!).commit()

        binding.bnvMain.setOnItemSelectedListener {

            // 최초 선택 시 fragment add, 선택된 프래그먼트 show, 나머지 프래그먼트 hide
            when(it.itemId){
                R.id.tab_home ->{
                    if(fragmentOneTest == null){
                        fragmentOneTest = FragmentOneTest()
                        fragmentManager.beginTransaction().add(R.id.fragmentContainerView,fragmentOneTest!!).commit()
                    }
                    if(fragmentOneTest != null) fragmentManager.beginTransaction().show(fragmentOneTest!!).commit()
                    if(fragmentTwoTest != null) fragmentManager.beginTransaction().hide(fragmentTwoTest!!).commit()

                    return@setOnItemSelectedListener true
                }
                R.id.tab_pet ->{
                    if(fragmentTwoTest == null){
                        fragmentTwoTest = FragmentTwoTest()
                        fragmentManager.beginTransaction().add(R.id.fragmentContainerView,fragmentTwoTest!!).commit()
                    }
                    if(fragmentOneTest != null) fragmentManager.beginTransaction().hide(fragmentOneTest!!).commit()
                    if(fragmentTwoTest != null) fragmentManager.beginTransaction().show(fragmentTwoTest!!).commit()

                    return@setOnItemSelectedListener true
                }
                else ->{
                    return@setOnItemSelectedListener true
                }
            }
        }
    }
}