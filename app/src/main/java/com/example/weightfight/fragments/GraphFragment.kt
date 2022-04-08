package com.example.weightfight.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weightfight.MainActivity
import com.example.weightfight.R
import com.example.weightfight.room_database.Data
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.LegendRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class GraphFragment : Fragment() {

    lateinit var graphView: GraphView

    lateinit var allData: MutableList<Data>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_graph, container, false)
        graphView = view.findViewById<GraphView>(R.id.graph)

        allData = getData()

        if (allData.isNotEmpty()) {
            val dataPoints: Array<DataPoint?> = arrayOfNulls<DataPoint?>(allData.size)
            for (i in 0 until allData.size) {
                dataPoints[i] = DataPoint(
                    allData[i].id.toDouble(),
                    allData[i].weight.toDouble()
                )
            }
            val series: LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>(dataPoints)
                .apply { title = "Вес (кг)/Сутки" }

            graphView.apply {
                addSeries(series)
                legendRenderer.isVisible = true
                legendRenderer.align = LegendRenderer.LegendAlign.TOP
            }
        }
        return view
    }

    fun getData() : MutableList<Data> = MainActivity.db.getDataDao().getAllData()
}