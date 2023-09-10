package com.example.petmate.home.petseeker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.databinding.ItemHomePetseekerSublistBinding
import com.example.petmate.home.petseeker.HomePetseekerListSubAdapter.PetseekerSubListViewHolder

class HomePetseekerListSubAdapter(val itemList: ArrayList<HomePetseekerListData>) : RecyclerView.Adapter<PetseekerSubListViewHolder>(){

    lateinit var binding: ItemHomePetseekerSublistBinding
    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetseekerSubListViewHolder {
        binding = ItemHomePetseekerSublistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetseekerSubListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: PetseekerSubListViewHolder, position: Int) {
        holder.setItem(itemList[position])
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    inner class PetseekerSubListViewHolder(binding: ItemHomePetseekerSublistBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setItem(item: HomePetseekerListData) {
            Glide.with(binding.itemHomePetseekerListImage)
                .load(item.list_img)                       // 불러올 이미지 URL
                .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                .placeholder(R.drawable.background_glide_init)             // 이미지 로딩 시작하기 전에 표시할 이미지
                .centerInside()                             // scaletype
                .into(binding.itemHomePetseekerListImage)  // 이미지를 넣을 뷰

            binding.itemHomePetseekerListBreed.text = item.list_breed
            binding.itemHomePetseekerListSex.setImageResource(getSexImage(item.list_sex))
            binding.itemHomePetseekerListColor.text = item.list_color
            binding.itemHomePetseekerListFeature.text = item.list_feature
        }
    }

    // 성별
    fun getSexImage(sex: String): Int {
        return when (sex) {
            "수컷" -> R.drawable.sex_male
            "암컷" -> R.drawable.sex_female
            else -> R.drawable.sex_male
        }
    }
}