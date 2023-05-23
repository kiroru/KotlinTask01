package jp.kiroru.kotlintask01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import jp.kiroru.kotlintask01.databinding.ActivityMainBinding


data class Item(val imageUrl: String, val name: String, val htmlUrl: String)

class MainActivity : AppCompatActivity(), CustomAdapter.ItemSelectionListener {

    private val tag = MainActivity::class.java.simpleName
    private val items = mutableListOf<Item>()
    private var adapter: CustomAdapter? = null
    private lateinit var binding: ActivityMainBinding

    private val webApiManagerListener = object : WebApiManager.Listener<List<GitHubUserEntity>> {

        override fun completed(entities: List<GitHubUserEntity>) {
            items.clear()
            for (entity in entities) {
                Log.d(tag, "$entity")
                items.add(Item(entity.avatar_url, entity.login, entity.html_url))
            }
            adapter?.notifyDataSetChanged()
        }

        override fun error(code: Int, description: String) {
            Log.d(tag, "code : $code description : $description")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CustomAdapter(this, items, this)
        binding.recyclerView.adapter = adapter

        setup()
    }

    private fun setup() {
        //
        // サーバーにリクエストする。
        //
        try {
            WebApiManager.getGitHubUsers(webApiManagerListener)
        } catch (e: Exception) {
            Log.e(tag, "エラー： " + e.localizedMessage)
        }
    }

    override fun notifyItemSelected(item: Item) {
        Toast.makeText(this, "${item.name} が選択されました。", Toast.LENGTH_SHORT).show()
    }

}