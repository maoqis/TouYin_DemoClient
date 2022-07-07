package com.yunlei.douyinlike.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Px
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ScrollState
import com.yunlei.douyinlike.R
import com.yunlei.douyinlike.adapter.ItemViewPagerAdapter
import com.yunlei.douyinlike.entity.ChangePageEvent
import kotlinx.android.synthetic.main.fragment_item.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.bundleOf

/**
 * @author Yun.Lei
 * @email waitshan@163.com
 * @date 2020/1/18
 */
class ItemFragment : Fragment() {


    companion object {
        private const val TAG = "ItemFragment"

        @JvmStatic
        fun getNewInstance(url: String): ItemFragment {
            val fragment = ItemFragment()
            fragment.arguments = bundleOf("url" to url)
            return fragment
        }
    }

    private var url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        url = arguments?.getString("url") ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemViewPager.adapter = ItemViewPagerAdapter(this, url)
        itemViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float,
                                        @Px positionOffsetPixels: Int) {
                Log.d(TAG, "onPageScrolled() called with: position = $position, positionOffset = $positionOffset, positionOffsetPixels = $positionOffsetPixels")
            }

            /**
             * This method will be invoked when a new page becomes selected. Animation is not
             * necessarily complete.
             *
             * @param position Position index of the new selected page.
             */
            override fun onPageSelected(position: Int) {
                Log.d(TAG, "onPageSelected() called with: position = $position")
            }

            /**
             * Called when the scroll state changes. Useful for discovering when the user begins
             * dragging, when a fake drag is started, when the pager is automatically settling to the
             * current page, or when it is fully stopped/idle. `state` can be one of [ ][.SCROLL_STATE_IDLE], [.SCROLL_STATE_DRAGGING] or [.SCROLL_STATE_SETTLING].
             */
            override fun onPageScrollStateChanged(@ScrollState state: Int) {
                Log.d(TAG, "onPageScrollStateChanged() called with: state = $state")
            }

        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventChangePage(event: ChangePageEvent) {
        itemViewPager.currentItem = event.position
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
