package com.explore.support.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.explore.support.databinding.DialogImageBinding

class ImageDialogfragment(
        private val image: Image
): DialogFragment(){

    lateinit var binding : DialogImageBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogImageBinding.inflate(inflater)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
//        binding.root.setBackgroundColor(resources.getColor(android.R.color.transparent))

//        binding.root.setBackgroundResource(android.R.color.transparent)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE,android.R.style.Theme_DeviceDefault_Dialog_MinWidth)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.idImage.loadImage(image){exception ->
            if (exception!=null){
            }
        }
        
        binding.idClose.setOnClickListener{
            dismiss()
        }
    }
}