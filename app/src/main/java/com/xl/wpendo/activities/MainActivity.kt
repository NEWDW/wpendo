package com.xl.wpendo.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.view.View
import android.widget.TextView
import com.xl.wpendo.R
import com.xl.wpendo.chenyang.text.RichTextActivity
import com.xl.wpendo.fragments.BaseFragment
import com.xl.wpendo.livedatabus.LiveDataBus
import com.xl.wpendo.noteitems.Note
import com.xl.wpendo.noteitems.NoteKind
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private lateinit var allFragment: BaseFragment
    private lateinit var agendaFragment: BaseFragment
    private lateinit var dailyFragment: BaseFragment
    private lateinit var essayFragment: BaseFragment
    private lateinit var todoFragment: BaseFragment
    private var fragmentList: MutableList<BaseFragment>?= ArrayList<BaseFragment>()
    private lateinit var tabLayout: TabLayout
    private lateinit var floatingActionButton: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragment()
        initView()
    }

    private fun initFragment() {
        allFragment = BaseFragment()
        fragmentList?.add(allFragment)
        agendaFragment = BaseFragment()
        agendaFragment
        fragmentList?.add(agendaFragment)
        dailyFragment = BaseFragment()
        fragmentList?.add(dailyFragment)
        essayFragment = BaseFragment()
        fragmentList?.add(essayFragment)
        todoFragment = BaseFragment()
        fragmentList?.add(todoFragment)
    }

    private fun initView() {
        bt.setOnClickListener {
            val note=Note("2019/7/20", NoteKind.item_essay, "please", "nothing",null)
            LiveDataBus.get().with("new_note").value=note
        }


        //fb点击到编辑页面
        floatingActionButton=findViewById(R.id.fb_add)
        floatingActionButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                startActivity(Intent(this@MainActivity,RichTextActivity::class.java))
            }

        })
        tabLayout = findViewById(R.id.bottom_tab_layout)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                val view = tabLayout.getTabAt(p0?.position!!)?.customView
                val textView = view?.findViewById<TextView>(R.id.tv_bottom_tab)!!
                val gradientDrawable = textView.background as GradientDrawable
                gradientDrawable.setColor(resources.getColor(R.color.colorAll))
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val position = p0?.position!!
                val view = tabLayout.getTabAt(position)?.customView
                val textView = view?.findViewById<TextView>(R.id.tv_bottom_tab)!!
                val gradientDrawable = textView.background as GradientDrawable
                when (position) {
                    1 -> gradientDrawable.setColor(resources.getColor(R.color.colorEssay))
                    2 -> gradientDrawable.setColor(resources.getColor(R.color.colorTodo))
                    3 -> gradientDrawable.setColor(resources.getColor(R.color.colorAgenda))
                    4 -> gradientDrawable.setColor(resources.getColor(R.color.colorDaily))
                }
                onTabItemSelect(position)
            }
        })
        for (i in 0 until 5) {
            tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.view_tab))
            //取消下划线
            tabLayout.setSelectedTabIndicatorHeight(0)
            //添加customView
            when (i) {
                0 -> tabLayout.getTabAt(i)!!.customView!!.findViewById<TextView>(R.id.tv_bottom_tab).text = "全部"
                1 -> tabLayout.getTabAt(i)!!.customView!!.findViewById<TextView>(R.id.tv_bottom_tab).text = "随笔"
                2 -> tabLayout.getTabAt(i)!!.customView!!.findViewById<TextView>(R.id.tv_bottom_tab).text = "待办"
                3 -> tabLayout.getTabAt(i)!!.customView!!.findViewById<TextView>(R.id.tv_bottom_tab).text = "日程"
                4 -> tabLayout.getTabAt(i)!!.customView!!.findViewById<TextView>(R.id.tv_bottom_tab).text = "日常"

            }
        }
    }

    @SuppressLint("CommitTransaction")
    private fun onTabItemSelect(position: Int) {
        supportFragmentManager.beginTransaction().replace(R.id.home_container, fragmentList?.get(position)!!).commit()

    }


}
