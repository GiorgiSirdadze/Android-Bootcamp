package com.example.homeworks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.homeworks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var pagerAdapter: OrdersPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
    }

    private fun setupViewPager() {
        viewPager = binding.viewPager
        pagerAdapter = OrdersPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateTabSelection(position)
            }
        })
    }

    private fun updateTabSelection(position: Int) {
        when (position) {
            0 -> {
                binding.tvActive.setTextColor(getColor(R.color.white))
                binding.underlineActive.setBackgroundColor(getColor(R.color.white))
                binding.tvCompleted.setTextColor(getColor(R.color.inactive_tab))
                binding.underlineCompleted.setBackgroundColor(getColor(R.color.inactive_tab))
            }
            1 -> {
                binding.tvCompleted.setTextColor(getColor(R.color.white))
                binding.underlineCompleted.setBackgroundColor(getColor(R.color.white))
                binding.tvActive.setTextColor(getColor(R.color.inactive_tab))
                binding.underlineActive.setBackgroundColor(getColor(R.color.inactive_tab))
            }
        }
    }
}
