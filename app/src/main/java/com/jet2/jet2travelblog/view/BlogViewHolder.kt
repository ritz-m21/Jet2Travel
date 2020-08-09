package com.jet2.jet2travelblog.view


import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.codelabs.paging.model.Article
import com.jet2.jet2travelblog.GlideRequests
import com.jet2.jet2travelblog.R

/**
 * View Holder for a [Article] RecyclerView list item.
 */
class BlogViewHolder(view: View, private val glide: GlideRequests) : RecyclerView.ViewHolder(view) {
    //Images
    private val imageViewAvatar: ImageView = view.findViewById(R.id.imageViewAvatar)
    private val imageViewArticle: ImageView = view.findViewById(R.id.imageViewArticle)

    //Textview
    private val textViewUsername: TextView = view.findViewById(R.id.textViewUsername)
    private val textViewSince: TextView = view.findViewById(R.id.textViewSince)

    private val textViewArticleTitle: TextView = view.findViewById(R.id.textViewArticleTitle)
    private val textViewComments: TextView = view.findViewById(R.id.textViewComments)

    private val textViewContents: TextView = view.findViewById(R.id.textViewContents)
    private val textViewLikes: TextView = view.findViewById(R.id.textViewLikes)

    private val textViewDesignation: TextView = view.findViewById(R.id.textViewDesignation)
    private val textViewUrl: TextView = view.findViewById(R.id.textViewUrl)

    private var article: Article? = null

    init {
        view.setOnClickListener {
            article?.media?.getOrNull(0)?.url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(article: Article?) {
        article?.let {
            showRepoData(it)
        }
    }

    private fun showRepoData(article: Article) {
        this.article = article

        textViewSince.text = article.createdAt
        textViewComments.text = article.comments.toString()

        textViewContents.text = article.content
        textViewLikes.text = article.likes.toString()

        //media
        article.media.getOrNull(0)?.apply {
            textViewArticleTitle.text = this.title
            textViewUrl.text = this.url

            if (this.image.startsWith("http")) {
                imageViewArticle.visibility = View.VISIBLE
                glide.load(this.image)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageViewArticle)
            } else {
                imageViewArticle.visibility = View.GONE
                glide.clear(imageViewArticle)
            }
        }

        //User
        article.user.getOrNull(0)?.apply {
            textViewUsername.text = String.format("%s %s", this.name, this.lastname)
            textViewDesignation.text = this.designation

            if (this.avatar.startsWith("http")) {
                imageViewAvatar.visibility = View.VISIBLE
                glide.load(this.avatar)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageViewAvatar)
            } else {
                imageViewAvatar.visibility = View.GONE
                glide.clear(imageViewAvatar)
            }
        }

    }

    companion object {
        fun create(parent: ViewGroup, glide: GlideRequests): BlogViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.article_item, parent, false)
            return BlogViewHolder(view, glide)
        }
    }
}
