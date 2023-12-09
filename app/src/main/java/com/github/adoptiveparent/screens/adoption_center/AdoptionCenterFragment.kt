package com.github.adoptiveparent.screens.adoption_center

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.github.adoptiveparent.R
import com.github.adoptiveparent.databinding.FragmentAdoptionCenterBinding

class AdoptionCenterFragment : Fragment() {
    private lateinit var binding: FragmentAdoptionCenterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdoptionCenterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.cardViewBase) {
            textViewTitle.text = getText(R.string.step_2_Title)
            textViewBig.text = getText(R.string.step_2_Big)
            textViewEnd.text = getText(R.string.step_2_End)
            imageViewTitle.setImageResource(R.drawable.ic_step_2_happy)
            imageViewTitle.scaleType = ImageView.ScaleType.FIT_CENTER
            imageViewEnd.setImageResource(R.drawable.ic_next_step_write)
            imageViewEnd.scaleType = ImageView.ScaleType.FIT_CENTER
        }
    }

}