
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.DetailActivity
import com.dicoding.mybottomnavtest.NewsResponse.ArticlesItem
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.databinding.ItemHomeHorizontalBinding

class HomeArticleAdapter(
    private var items: List<ArticlesItem> = emptyList()
) : RecyclerView.Adapter<HomeArticleAdapter.HomeArticleViewHolder>() {

    class HomeArticleViewHolder(val binding: ItemHomeHorizontalBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeArticleViewHolder {
        val binding = ItemHomeHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (items.isEmpty()) 5 else items.size
    }

    override fun onBindViewHolder(holder: HomeArticleViewHolder, position: Int) {
        if (items.isNotEmpty() && position < items.size) {
            val article = items[position]
            with(holder.binding) {
                tvHomeHorizontal.text = article.title ?: "No Title"

                Glide.with(ivHomeHorizontal.context)
                    .load(article.imageUrl)
                    .placeholder(R.drawable.no_content)
                    .error(R.drawable.error_image)
                    .into(ivHomeHorizontal)

                root.setOnClickListener {
                    val articleId = article.id
                    val context = root.context
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("EVENT_ID", articleId)
                    context.startActivity(intent)
                }
            }
        } else {
            with(holder.binding) {
                tvHomeHorizontal.text = "Error Articles"
                Glide.with(ivHomeHorizontal.context)
                    .load(R.drawable.lokajamulogo)
                    .placeholder(R.drawable.lokajamulogo)
                    .error(R.drawable.error_image)
                    .into(ivHomeHorizontal)

                root.setOnClickListener {

                }
            }
        }
    }

    fun updateData(newItems: List<ArticlesItem>) {
        items = if (newItems.isEmpty()) {
            List(5) {
                ArticlesItem(
                    createdAt = "2024-01-01",
                    contents = null,
                    imageUrl = null,
                    id = -1,
                    title = "Dummy Title ${it + 1}",
                    tags = listOf("dummy", "placeholder"),
                    updatedAt = "2024-01-01"
                )
            }
        } else {
            newItems
        }
        notifyDataSetChanged()
    }
}



