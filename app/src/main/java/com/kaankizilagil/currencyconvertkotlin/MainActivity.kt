package com.kaankizilagil.currencyconvertkotlin

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getRatesButtonClick(view : View) {

        val download = DownloadData()

        try {

            val url = "http://data.fixer.io/api/latest?access_key=80d80acf451d38ee27aef1f3d5f50dee&format=1"
            download.execute(url)

        } catch (e : Exception) {

            e.printStackTrace()
        }
    }

    inner class DownloadData : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String?): String {

            var result = ""
            var url : URL
            var httpURLConnection : HttpURLConnection

            try {

                url = URL(params[0])
                httpURLConnection = url.openConnection() as HttpURLConnection   // Casting
                val inputStream = httpURLConnection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)

                var data = inputStreamReader.read()

                while(data > 0) {

                    val character = data.toChar()
                    result = result + character
                    data = inputStreamReader.read()
                }
                return result

            } catch (e : Exception) {

                e.printStackTrace()

                return result
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            try {

                val jsonObject = JSONObject(result)

                /*
                val date = jsonObject.getString("date")
                println(date)
                */

                /*
                val base = jsonObject.getString("base")
                */

                val rates = jsonObject.getString("rates")

                val jsonObjectForMoneys = JSONObject(rates)

                val cad = jsonObjectForMoneys.getString("CAD")
                cadText.text = "CAD : ${cad}"

                val usd = jsonObjectForMoneys.getString("USD")
                usdText.text = "USD : ${usd}"

                val tl = jsonObjectForMoneys.getString("TRY")
                tlText.text  = "TRY  : ${tl}"

                val chf = jsonObjectForMoneys.getString("CHF")
                chfText.text = "CHF : ${chf}"

            } catch (e : Exception) {

                e.printStackTrace()
            }
        }
    }
}
