package com.github.adoptiveparent.screens.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.github.adoptiveparent.R
import com.github.adoptiveparent.databinding.FragmentHelpBinding

class HelpFragment : Fragment() {
    private lateinit var binding: FragmentHelpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHelpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.cardViewBase) {
            textViewTitle.text = getText(R.string.step_6_Title)
            textViewBig.text = getText(R.string.step_6_Big)
            textViewEnd.text = getText(R.string.step_6_End)
            imageViewTitle.setImageResource(R.drawable.ic_help_call)
            imageViewTitle.scaleType = ImageView.ScaleType.FIT_CENTER
            imageViewEnd.setImageResource(R.drawable.ic_next_step_problem_solving)
            imageViewEnd.scaleType = ImageView.ScaleType.FIT_CENTER
        }
    }
}