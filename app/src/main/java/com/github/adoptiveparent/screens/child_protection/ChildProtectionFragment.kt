package com.github.adoptiveparent.screens.child_protection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.github.adoptiveparent.R
import com.github.adoptiveparent.databinding.FragmentChildProtectionBinding


class ChildProtectionFragment : Fragment() {
    private lateinit var binding: FragmentChildProtectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChildProtectionBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.cardViewBase) {
            textViewTitle.text = getText(R.string.step_1_Title)
            textViewBig.text = getText(R.string.step_1_Big)
            textViewEnd.text = getText(R.string.step_1_End)
            imageViewTitle.setImageResource(R.drawable.ic_step_1_airport)
            imageViewTitle.scaleType = ImageView.ScaleType.FIT_CENTER
            imageViewEnd.setImageResource(R.drawable.ic_next_step_write)
            imageViewEnd.scaleType = ImageView.ScaleType.FIT_CENTER
        }
    }
}