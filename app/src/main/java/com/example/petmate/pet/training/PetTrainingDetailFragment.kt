package com.example.petmate.pet.training

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petmate.R
import com.example.petmate.VerticalItemDecorator
import com.example.petmate.databinding.FragmentPetTrainingDetailBinding

class PetTrainingDetailFragment : Fragment() {

    lateinit var binding: FragmentPetTrainingDetailBinding
    private val TAG = "PetTrainingDetailFragment123"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this){
            findNavController().navigate(R.id.action_petTrainingDetailFragment_to_petTrainingFragment)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bundle = arguments
        val obj = bundle?.getParcelable<PetTrainingInterfaceResponseResult>("trainingInfo")
        Log.d(TAG, "PetTrainingDetailFragment onCreateView: ${obj}")
        Log.d(TAG, "${obj}")
        if (obj != null) {
            Log.d(TAG, "${obj.trainingIdx}")
            Log.d(TAG, "${obj.name}")
            Log.d(TAG, "${obj.level}")
            Log.d(TAG, "${obj.detail}")
            Log.d(TAG, "${obj.url}")
        }


        binding = FragmentPetTrainingDetailBinding.inflate(inflater)

        val boardAdapter = PetTrainingDetailAdapter(getTrainingDetailList())
        boardAdapter.notifyDataSetChanged()

        binding.rcvTrainingDetailWays.adapter = boardAdapter
        binding.rcvTrainingDetailWays.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvTrainingDetailWays.addItemDecoration(VerticalItemDecorator(5))
        return binding.getRoot()
    }

    private fun getTrainingDetailList(): ArrayList<PetTrainingDetailData>{
        val trainingDetailList = ArrayList<PetTrainingDetailData>()

        trainingDetailList.add(PetTrainingDetailData("1단계","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
        trainingDetailList.add(PetTrainingDetailData("2단계","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
        trainingDetailList.add(PetTrainingDetailData("3단계","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))

        return trainingDetailList
    }
}