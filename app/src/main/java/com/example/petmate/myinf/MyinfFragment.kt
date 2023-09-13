package com.example.petmate.myinf

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petmate.HorizontalItemDecorator
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.VerticalItemDecorator
import com.example.petmate.databinding.FragmentMyinfBinding

class MyinfFragment : Fragment() {

    lateinit var binding: FragmentMyinfBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyinfBinding.inflate(inflater)

        var adapterMyinfPicList = MyinfPicListAdapter(getPicList())
        adapterMyinfPicList.notifyDataSetChanged()
        var adapterMyinfUserList = MyinfUserListAdapter(getUserList())
        adapterMyinfUserList.notifyDataSetChanged()

        binding.rcvMyinfPicList.adapter = adapterMyinfPicList
        binding.rcvMyinfPicList.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rcvMyinfPicList.addItemDecoration(VerticalItemDecorator(2))
        binding.rcvMyinfPicList.addItemDecoration(HorizontalItemDecorator(2))

        adapterMyinfPicList.setItemClickListener(object : OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                v.findNavController().navigate(R.id.action_myinfFragment_to_myinfPostFragment)            }
        })

        binding.rcvMyinfUserList.adapter = adapterMyinfUserList
        binding.rcvMyinfUserList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.btnPost.setOnClickListener{
            val intent = Intent(requireContext(), MyinfPhotoActivity::class.java)
            startActivity(intent)
        }


        return binding.getRoot()
    }

    private fun getPicList(): ArrayList<MyinfPicListData>{
        val picList = ArrayList<MyinfPicListData>()

        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))

        return picList
    }

    private fun getUserList(): ArrayList<MyinfUserListData>{
        val userList = ArrayList<MyinfUserListData>()

        userList.add(MyinfUserListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        userList.add(MyinfUserListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        userList.add(MyinfUserListData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))

        return userList
    }
}