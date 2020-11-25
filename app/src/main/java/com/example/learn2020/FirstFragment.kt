package com.example.learn2020

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import java.lang.ref.WeakReference

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        val handler = Handler {
            when (it.what) {
                1 -> print("1111")
                2 -> print("222")
            }
            true
        }
        handler.post {
            activity?.runOnUiThread{}
        }
        handler.sendEmptyMessage(1)
    }

     class MyHandler(private val  mActivityRef : WeakReference<Fragment>) : Handler() {

         val fragment = mActivityRef.get()
     }
}