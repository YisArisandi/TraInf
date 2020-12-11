package com.example.trainf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.trainf.api.RetrofitClient
import com.example.trainf.fragments.AbtFragment
import com.example.trainf.fragments.InfFragment
import com.example.trainf.fragments.TraFragment
import com.example.trainf.model.IndonesiaResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_inf.*
import kotlinx.android.synthetic.main.fragment_tra.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    var traFragment = TraFragment()
    var infFragment = InfFragment()
    var abtFragment = AbtFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


val button = findViewById<Button>(R.id.btnProvince)
        button.setOnClickListener {
            val intent = Intent(this, TraFragment::class.java)
            startActivity(intent)
        }

//        api
        showIndonesia()



//        fragment tombol
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bt_navv)




        traFragment = TraFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, traFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

//tombol menu
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.ic_tra -> {
                    traFragment = TraFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, traFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.ic_inf -> {
                    infFragment = InfFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, infFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.ic_abt -> {
                    abtFragment = AbtFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, abtFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
            true
        }




//        btnProvince.setOnClickListener {
//            Intent(this@MainActivity, ProvinceActivity::class.java).also {
//                startActivity(it)
//            }
//        }

    }



    //    api
    private fun showIndonesia() {
        RetrofitClient.instance.getIndonesia()
            .enqueue(object : Callback<ArrayList<IndonesiaResponse>> {
                override fun onResponse(
                    call: Call<ArrayList<IndonesiaResponse>>,
                    response: Response<ArrayList<IndonesiaResponse>>
                ) {
                    val indonesia = response.body()?.get(0)
                    val positive = indonesia?.positif
                    val hoszpital = indonesia?.dirawat
                    val recover = indonesia?.sembuh
                    val death = indonesia?.meninggal

                    tvPositive.text = positive
                    tvHospitalized.text = hoszpital
                    tvRecover.text = recover
                    tvDeath.text = death
                }

                override fun onFailure(call: Call<ArrayList<IndonesiaResponse>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()


                }


            })

    }


}