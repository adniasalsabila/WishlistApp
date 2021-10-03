package com.gits.mywishlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gits.mywishlist.model.DataItem
import com.gits.mywishlist.model.DefaultResponse
import com.gits.mywishlist.model.GetListResponse
import com.gits.mywishlist.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val myRecyclerViewAdapter = RecyclerViewAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = myRecyclerViewAdapter
        getDataWishlist()

        buttonAdd.setOnClickListener {
            RetrofitClient.instance.insertWishlist(
                "0",
                textFieldItem.editText?.text.toString().trim(),
                textFieldCost.editText?.text.toString().toInt(),
                "insert_wishlist"
            ).enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    if (response!!.body()?.status == 1) {
                        Toast.makeText(this@MainActivity, "Berhasil Ditambahkan", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(this@MainActivity, "Gagal Ditambahkan", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Response : ${t}", Toast.LENGTH_LONG).show()
                }
            })
        }

        textFieldItem.editText?.setText(intent.getStringExtra("NAMEITEM"))
        textFieldCost.editText?.setText(intent.getStringExtra("COST"))

        if (intent.action == "Edit") {
            buttonAdd.setText("Edit")
            buttonAdd.setOnClickListener {
                RetrofitClient.instance.updateWishlist(
                    intent.getStringExtra("ID").toString(),
                    textFieldItem.editText?.text.toString().trim(),
                    textFieldCost.editText?.text.toString().toInt(),
                    "update_wishlist"
                ).enqueue(object : Callback<DefaultResponse> {
                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        if (response!!.isSuccessful) {
                            if (response!!.body()?.status == 1) {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Data Berhasil Diubah",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Data Gagal Diubah",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Data Gagal Diubah",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Response : ${t}", Toast.LENGTH_LONG)
                            .show()
                    }

                })
            }
        } else {
            buttonAdd.setText("Tambah")
        }


    }

    private fun getDataWishlist() {
        RetrofitClient.instance.getListWishlist()
            .enqueue(object : Callback<GetListResponse> {
                override fun onResponse(
                    call: Call<GetListResponse>?,
                    response: Response<GetListResponse>?
                ) {
                    if (response!!.isSuccessful) {
                        response.body()?.let { tampilMovie(it) }
                        val result = response.body()?.data
                        for (item in result!!) {
                            detailItem(item!!.id)
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Reponse Gagal", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<GetListResponse>?, t: Throwable?) {
                    Toast.makeText(this@MainActivity, "Reponse Gagal : ${t}", Toast.LENGTH_LONG)
                        .show()
                }

            })
    }

    private fun tampilMovie(data: GetListResponse) {
        val result = data.data
        myRecyclerViewAdapter.setData(result as List<DataItem>)
    }

    private fun detailItem(id: String?) {
        RetrofitClient
    }
}