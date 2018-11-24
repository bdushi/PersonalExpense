package al.bruno.personal.expense

import al.bruno.personal.expense.callback.OnSwipeItemListener
import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable

class SimpleItemTouchHelper : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    private var xMarkMargin: Float = 0F

    private var rightToLeftColor: Int = 0
    private var rightToLeftIcon: Drawable? = null

    private var leftToRightColor: Int = 0
    private var leftToRightIcon: Drawable? = null

    private var onLeftItemSwipeListener: OnSwipeItemListener? = null
    private var onRightItemSwipeListener: OnSwipeItemListener? = null


    class Builder {
        private var xMarkMargin: Float = 0F
        private var rightToLeftColor: Int = 0
        private var rightToLeftIcon: Drawable? = null

        private var leftToRightColor: Int = 0
        private var leftToRightIcon: Drawable? = null

        fun setXMarkMargin(float: Float): SimpleItemTouchHelper.Builder {
            xMarkMargin = float
            return this
        }

        fun setRightToLeftColor(int: Int): SimpleItemTouchHelper.Builder {
            rightToLeftColor = int
            return this
        }
        fun setRightToLeftIcon(drawable: Drawable): SimpleItemTouchHelper.Builder {
            rightToLeftIcon = drawable
            return this
        }
        fun setLeftToRightColor(int: Int): SimpleItemTouchHelper.Builder {
            leftToRightColor = int
            return this
        }
        fun setLeftToRightIcon(drawable: Drawable): SimpleItemTouchHelper.Builder {
            leftToRightIcon = drawable
            return this
        }

        fun build() : SimpleItemTouchHelper {
            return newInstance(xMarkMargin, rightToLeftColor, rightToLeftIcon, leftToRightColor, leftToRightIcon)
        }
    }

    companion object {
        private fun newInstance(xMarkMargin: Float, rightToLeftColor: Int, rightToLeftIcon: Drawable?, leftToRightColor: Int, leftToRightIcon: Drawable?) : SimpleItemTouchHelper{
            val simpleItemTouchHelper = SimpleItemTouchHelper()
            simpleItemTouchHelper.xMarkMargin = xMarkMargin;
            simpleItemTouchHelper.rightToLeftColor = rightToLeftColor;
            simpleItemTouchHelper.rightToLeftIcon = rightToLeftIcon;
            simpleItemTouchHelper.leftToRightColor = leftToRightColor;
            simpleItemTouchHelper.leftToRightIcon = leftToRightIcon;
            return simpleItemTouchHelper;
        }
    }

    fun onLeftSwipeItemListener(onLeftItemSwipeListener: OnSwipeItemListener) : SimpleItemTouchHelper {
        this.onLeftItemSwipeListener = onLeftItemSwipeListener
        return this;
    }
    fun onRightSwipeItemListener(onRightItemSwipeListener: OnSwipeItemListener) : SimpleItemTouchHelper {
        this.onRightItemSwipeListener = onRightItemSwipeListener
        return this;
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false;
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (direction == ItemTouchHelper.LEFT) {
            onLeftItemSwipeListener!!.onItemSwiped(position)
        } else if (direction == ItemTouchHelper.RIGHT) {
            onRightItemSwipeListener!!.onItemSwiped(position)
        }
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val itemView = viewHolder.itemView
            val itemHeight = itemView.bottom - itemView.top
            if (dX < 0) {
                val background = ColorDrawable(rightToLeftColor)
                background.setBounds((itemView.right + dX).toInt(), itemView.top, itemView.right, itemView.bottom)
                background.draw(c)

                val intrinsicWidth = rightToLeftIcon!!.intrinsicWidth
                val intrinsicHeight = rightToLeftIcon!!.intrinsicHeight
                val iconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
                rightToLeftIcon!!.setBounds((itemView.right - xMarkMargin - intrinsicWidth).toInt(), iconTop, (itemView.right - xMarkMargin).toInt(), iconTop + intrinsicHeight)
                rightToLeftIcon!!.draw(c)
            } else {
                val background = ColorDrawable(leftToRightColor)
                background.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                background.draw(c)

                val intrinsicWidth = leftToRightIcon!!.getIntrinsicWidth()
                val intrinsicHeight = leftToRightIcon!!.getIntrinsicWidth()

                val iconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
                leftToRightIcon!!.setBounds((itemView.left + xMarkMargin).toInt(), iconTop, (itemView.left + xMarkMargin + intrinsicWidth).toInt(), iconTop + intrinsicHeight)
                leftToRightIcon!!.draw(c)
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}