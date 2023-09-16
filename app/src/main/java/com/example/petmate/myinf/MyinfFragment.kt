package com.example.petmate.myinf

import android.content.Intent
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.petmate.GlobalUserIdx
import com.example.petmate.HorizontalItemDecorator
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.RightItemDecorator
import com.example.petmate.VerticalItemDecorator
import com.example.petmate.community.CommunityInterface
import com.example.petmate.community.CommunityInterfaceResponse
import com.example.petmate.community.CommunityPopularAdapter
import com.example.petmate.community.CommunityPopularData
import com.example.petmate.databinding.FragmentMyinfBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.Date
import kotlin.concurrent.thread
import kotlin.random.Random

class MyinfFragment : Fragment() {

    lateinit var binding: FragmentMyinfBinding
    private val TAG = "MyinfFragment123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyinfBinding.inflate(inflater)

        val userIdx = GlobalUserIdx.getUserIdx()

        requestUserInfo(userIdx)

        //var adapterMyinfPicList = MyinfPicListAdapter(getPicList())
        //adapterMyinfPicList.notifyDataSetChanged()
        var adapterMyinfUserList = MyinfUserListAdapter(getUserList())
        adapterMyinfUserList.notifyDataSetChanged()

        //binding.rcvMyinfPicList.adapter = adapterMyinfPicList
        binding.rcvMyinfPicList.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rcvMyinfPicList.addItemDecoration(VerticalItemDecorator(2))
        binding.rcvMyinfPicList.addItemDecoration(HorizontalItemDecorator(2))

        /*adapterMyinfPicList.setItemClickListener(object : OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                v.findNavController().navigate(R.id.action_myinfFragment_to_myinfPostFragment)
            }
        })*/

        binding.rcvMyinfUserList.adapter = adapterMyinfUserList
        binding.rcvMyinfUserList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.btnPost.setOnClickListener {
            val intent = Intent(requireContext(), MyinfPhotoActivity::class.java)
            startActivity(intent)
        }

        requestPicList(userIdx)
        requestUserList(userIdx)

        return binding.getRoot()
    }

    private fun requestUserList(userIdx: Int) {
//고정
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.16.204:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //

        val service = retrofit.create(MyinfInterface::class.java);
        service.getUserList(userIdx).enqueue(object : Callback<MyinfUserListInerfaceResponse> {

            override fun onResponse(call: Call<MyinfUserListInerfaceResponse>, response: retrofit2.Response<MyinfUserListInerfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: MyinfUserListInerfaceResponse? = response.body()
                    Log.d(TAG, "PopularList onResponse 성공: " + result?.toString());


                    when (result?.code) {
                        200 -> {
                            val list = ArrayList<MyinfUserListData>()
                            for(item in result.result){
                                list.add(MyinfUserListData(item.image))
                            }

                            val adapterMyinfUserList = MyinfUserListAdapter(list)

                            binding.rcvMyinfUserList.adapter = adapterMyinfUserList
                            binding.rcvMyinfUserList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

                            adapterMyinfUserList.notifyDataSetChanged()

                        }

                        else -> {
                            Log.d(TAG, "onResponse: ㅈ버그발생 보내는 데이터가 문제임 ")
                        }
                    }

                } else {
                    // 통신이 실패한 경우(응답 코드 3xx, 4xx 등)
                    Log.d(TAG, "onResponse 실패" + response.code())
                }
            }

            override fun onFailure(call: Call<MyinfUserListInerfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString());
            }
        })
    }

    private fun requestUserInfo(userIdx: Int) {
        //고정
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.16.204:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //

        val service = retrofit.create(MyinfInterface::class.java);
        service.getUserInfo(userIdx).enqueue(object : Callback<MyinfUserInterfaceResponse> {

            override fun onResponse(call: Call<MyinfUserInterfaceResponse>, response: retrofit2.Response<MyinfUserInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: MyinfUserInterfaceResponse? = response.body()
                    Log.d(TAG, "PopularList onResponse 성공: " + result?.toString());


                    when (result?.code) {
                        200 -> {
                            val item = result.result[0]

                            binding.nameMyinfUser.text = item.name
                            binding.nickNameMyinfUser.text = item.nickName
                            binding.lineInfoMyinfUser.text = item.lineInfo

                            if(item.image.isBlank() || item.image == ""){
                                val tempimagelist = ArrayList<String>()
                                tempimagelist.add("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg")
                                tempimagelist.add("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg")
                                tempimagelist.add("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg")
                                Glide.with(binding.imgMyinfUser)
                                    .load(tempimagelist.get(Random.nextInt(0,3)))                         // 불러올 이미지 URL
                                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                                    .centerInside()                                 // scaletype
                                    .into(binding.imgMyinfUser)             // 이미지를 넣을 뷰
                            }else if(item.image.endsWith(".png") ||item.image.endsWith(".jpg")||item.image.endsWith(".jpeg") ) {
                                Glide.with(binding.imgMyinfUser)
                                    .load(item.image)                         // 불러올 이미지 URL
                                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                                    .centerInside()                                 // scaletype
                                    .into(binding.imgMyinfUser)             // 이미지를 넣을 뷰
                            }else{
                                val encodeByte = Base64.decode(item.image, Base64.NO_WRAP)
                                val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                                binding.imgMyinfUser.setImageBitmap(bitmap)
                            }
                        }

                        else -> {
                            Log.d(TAG, "onResponse: ㅈ버그발생 보내는 데이터가 문제임 ")
                        }
                    }

                } else {
                    // 통신이 실패한 경우(응답 코드 3xx, 4xx 등)
                    Log.d(TAG, "onResponse 실패" + response.code())
                }
            }

            override fun onFailure(call: Call<MyinfUserInterfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString());
            }
        })
    }

    private fun requestPicList(userIdx: Int) {
//고정
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.16.204:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //

        val service = retrofit.create(MyinfInterface::class.java);
        service.getPicList(userIdx).enqueue(object : Callback<MyinfPicInterfaceResponse> {

            override fun onResponse(call: Call<MyinfPicInterfaceResponse>, response: retrofit2.Response<MyinfPicInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: MyinfPicInterfaceResponse? = response.body()
                    Log.d(TAG, "PopularList onResponse 성공: " + result?.toString());


                    when (result?.code) {
                        200 -> {
                            val resultlist = result.result

                            val adapterMyinfPicList = MyinfPicListAdapter(resultlist)

                            binding.rcvMyinfPicList.adapter = adapterMyinfPicList
                            binding.rcvMyinfPicList.layoutManager = GridLayoutManager(requireContext(), 3)
                            binding.rcvMyinfPicList.addItemDecoration(VerticalItemDecorator(2))
                            binding.rcvMyinfPicList.addItemDecoration(HorizontalItemDecorator(2))

                            adapterMyinfPicList.setItemClickListener(object : OnItemClickListener {
                                override fun onClick(v: View, position: Int) {
                                    //TODO("bundle 수정해야함 커뮤니티에 있는 post로 넘어가는게 아니라 myinfpost로 이동하는거임")
                                    val bundle = Bundle()
                                    bundle.putParcelableArrayList("Postdata", resultlist)
                                    Log.d(TAG, "onClick bundle item : ${resultlist}")
                                    findNavController().navigate(R.id.action_myinfFragment_to_myinfPostFragment,bundle)
                                }
                            })
                            adapterMyinfPicList.notifyDataSetChanged()
                        }

                        else -> {
                            Log.d(TAG, "onResponse: ㅈ버그발생 보내는 데이터가 문제임 ")
                        }
                    }

                } else {
                    // 통신이 실패한 경우(응답 코드 3xx, 4xx 등)
                    Log.d(TAG, "onResponse 실패" + response.code())
                }
            }

            override fun onFailure(call: Call<MyinfPicInterfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString());
            }
        })
    }

    private fun getPicList(): ArrayList<MyinfPicListData> {
        val picList = ArrayList<MyinfPicListData>()

        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))

        return picList
    }

    private fun getUserList(): ArrayList<MyinfUserListData> {
        val userList = ArrayList<MyinfUserListData>()

        userList.add(MyinfUserListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        userList.add(MyinfUserListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        userList.add(MyinfUserListData("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))

        return userList
    }
}