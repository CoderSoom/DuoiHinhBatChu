package com.demo.duoihinhbatchu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var listData = arrayListOf<Question>()
    private var count = 0
    private var listAnswer = arrayListOf<Char>()
    private var numberQuestion = 0
    private var check = ""
    private var point = 0
    private var heart = 5
    var a = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initQuestions()
        updateQuestionContent()
        updateText()
        deleteBtn()
        btn_next.setOnClickListener(this)
a = 10
    }

    private fun updateText() {

        val random = Random()
        for (i in 0..15 - numberQuestion) {
            listAnswer.add((random.nextInt(26) + 65).toChar())
        }
        for (i in 0..numberQuestion - 1) {
            listAnswer.add(listData.get(count).name[i])
        }
        Collections.sort(listAnswer)
        for (i in 0..llSuggest1.childCount - 1) {
            (llSuggest1.getChildAt(i) as Button).text = listAnswer.get(i).toString()
        }
        for (i in 0..llSuggest2.childCount - 1) {
            (llSuggest2.getChildAt(i) as Button).text = listAnswer.get(i + 8).toString()
        }
    }

    private fun checkAnser(i: Int, x: Int) {
        var answer = ""
        if (i < 20) {
            answer += (llSuggest1.getChildAt(i) as Button).text
        }
        if (x < 20) {
            answer += (llSuggest2.getChildAt(x) as Button).text
        }
        check += answer
    }

    private fun showAnswer() {
        tv_show_answer.visibility = View.VISIBLE
        btn_next.visibility = View.VISIBLE
        if (check.equals(listData.get(count).name)) {
            tv_show_answer.text = "Bạn đã tìm ra đáp án!"
            point += 100
            tv_coin.text = point.toString()
            for (i in 0 until ll_1.childCount) {
                (ll_1.getChildAt(i) as Button).background =
                    this.resources.getDrawable(R.drawable.tile_true)
            }
            for (i in 0..ll_2.childCount - 1) {
                (ll_2.getChildAt(i) as Button).background =
                    this.resources.getDrawable(R.drawable.tile_true)
            }

        } else {
            heart = heart - 1
            tv_heart.text = heart.toString()
            tv_show_answer.text = "Bạn đã chọn sai đáp án rồi!"
            for (i in 0..ll_1.childCount - 1) {
                (ll_1.getChildAt(i) as Button).background =
                    this.resources.getDrawable(R.drawable.tile_false)
            }
            for (i in 0..ll_2.childCount - 1) {
                (ll_2.getChildAt(i) as Button).background =
                    this.resources.getDrawable(R.drawable.tile_false)
            }
        }
        if (heart <= 0) {
            val builder =
                AlertDialog.Builder(this)
            builder.setMessage("Bạn đã thua !")
            builder.setPositiveButton(
                "Ok"
            ) { dialog, which ->
                finish()
            }
            builder.create().show()

        }
        if (point == listData.size * 100) {
            val builder =
                AlertDialog.Builder(this)
            builder.setMessage("Bạn đã chiến thắng !")
            builder.setPositiveButton(
                "Ok"
            ) { dialog, which ->
             finish()
            }
            builder.create().show()
        }

    }

    private fun resetBtn() {
        for (i in 0..ll_1.childCount - 1) {
            (ll_1.getChildAt(i) as Button).background =
                this.resources.getDrawable(R.drawable.tile_gray)
            (ll_1.getChildAt(i) as Button).text = ""
        }
        for (i in 0..ll_2.childCount - 1) {
            (ll_2.getChildAt(i) as Button).background =
                this.resources.getDrawable(R.drawable.tile_gray)
            (ll_2.getChildAt(i) as Button).text = ""
        }
        for (i in 0 until llSuggest1.childCount) {
            (llSuggest1.getChildAt(i) as Button).visibility = View.VISIBLE

        }
        for (i in 0..llSuggest2.childCount - 1) {
            (llSuggest2.getChildAt(i) as Button).visibility = View.VISIBLE

        }
        ll_1.removeAllViews()
        ll_2.removeAllViews()

        check = ""
        listAnswer.clear()
        tv_show_answer.visibility = View.INVISIBLE
    }


    private fun deleteBtn() {
        var testClick = 0
        var testClick2 = 0
        var test = 0
        for (i in 0..llSuggest1.childCount - 1) {
            (llSuggest1.getChildAt(i) as Button).setOnClickListener {
                if (testClick < ll_1.childCount) {
                    (ll_1.getChildAt(testClick) as Button).text =
                        (llSuggest1.getChildAt(i) as Button).text
                    checkAnser(i, 30)
                    it.visibility = View.INVISIBLE
                    testClick++
                    test = i

                }
                if (testClick == ll_1.childCount && testClick2 < ll_2.childCount) {
                    if (i != test) {
                        (ll_2.getChildAt(testClick2) as Button).text =
                            (llSuggest1.getChildAt(i) as Button).text
                        testClick2++
                        checkAnser(i, 30)
                        it.visibility = View.INVISIBLE
                    }
                }
                if (testClick + testClick2 == numberQuestion ) {
                    showAnswer()
                }

            }
        }


        for (i in 0..llSuggest2.childCount - 1) {
            (llSuggest2.getChildAt(i) as Button).setOnClickListener {
                if (testClick < ll_1.childCount) {
                    (ll_1.getChildAt(testClick) as Button).text =
                        (llSuggest2.getChildAt(i) as Button).text
                    checkAnser(30, i)
                    it.visibility = View.INVISIBLE
                    testClick++
                    test = i

                }
                if (testClick == ll_1.childCount && testClick2 < ll_2.childCount) {
                    if (i != test) {
                        (ll_2.getChildAt(testClick2) as Button).text =
                            (llSuggest2.getChildAt(i) as Button).text
                        testClick2++
                        checkAnser(30, i)
                        it.visibility = View.INVISIBLE
                    }
                }
                if (testClick + testClick2 == numberQuestion ) {
                    showAnswer()
                }

            }

        }
    }

    private fun updateQuestionContent() {
        ivImg.setImageResource(listData.get(count).img)
        numberQuestion = listData.get(count).name.length
        for (i in 0..Math.min(8 - 1, numberQuestion - 1)) {
            val btn = LayoutInflater.from(this)
                .inflate(
                    R.layout.button_anwser,
                    ll_1, false
                ) as Button
            ll_1.addView(btn)
        }
        for (i in 8..numberQuestion - 1) {
            val btn = LayoutInflater.from(this)
                .inflate(
                    R.layout.button_anwser,
                    ll_2, false
                ) as Button
            ll_2.addView(btn)
        }
    }

    private fun initQuestions() {
        listData.add(
            Question("tichphan".toUpperCase(), R.drawable.tichphan)
        )
        listData.add(
            Question("thattinh".toUpperCase(), R.drawable.thattinh)
        )
        listData.add(
            Question("thothe".toUpperCase(), R.drawable.thothe)
        )
        listData.add(
            Question("songsong".toUpperCase(), R.drawable.songsong)
        )
        listData.add(
            Question("quyhang".toUpperCase(), R.drawable.quyhang)
        )
        listData.add(
            Question("oto".toUpperCase(), R.drawable.oto)
        )
        listData.add(
            Question("xakep".toUpperCase(), R.drawable.xakep)
        )
        listData.add(
            Question("xaphong".toUpperCase(), R.drawable.xaphong)
        )
        listData.add(
            Question("tranhthu".toUpperCase(), R.drawable.tranhthu)
        )
        listData.add(
            Question("tohoai".toUpperCase(), R.drawable.tohoai)
        )
        listData.add(
            Question("nambancau".toUpperCase(), R.drawable.nambancau)
        )
        listData.add(
            Question("kiemchuyen".toUpperCase(), R.drawable.kiemchuyen)
        )
        listData.add(
            Question("lancan".toUpperCase(), R.drawable.lancan)
        )
        listData.add(
            Question("totien".toUpperCase(), R.drawable.totien)
        )
        listData.add(
            Question("hoidong".toUpperCase(), R.drawable.hoidong)
        )
        listData.add(
            Question("khoailang".toUpperCase(), R.drawable.khoailang)
        )
        listData.add(
            Question("hongtam".toUpperCase(), R.drawable.hongtam)
        )
        listData.add(
            Question("giandiep".toUpperCase(), R.drawable.giandiep)
        )
        listData.add(
            Question("giangmai".toUpperCase(), R.drawable.giangmai)
        )
        listData.add(
            Question("vuaphaluoi".toUpperCase(), R.drawable.vuaphaluoi)
        )
        listData.add(
            Question("danong".toUpperCase(), R.drawable.danong)
        )
        listData.add(
            Question("danhlua".toUpperCase(), R.drawable.danhlua)
        )
        listData.add(
            Question("chieutre".toUpperCase(), R.drawable.chieutre)
        )
        listData.add(
            Question("aomua".toUpperCase(), R.drawable.aomua)
        )
        listData.add(
            Question("baocao".toUpperCase(), R.drawable.baocao)
        )
        listData.add(
            Question("vuonbachthu".toUpperCase(Locale.ROOT), R.drawable.vuonbachthu)
        )
        listData.add(
            Question("canthiep".toUpperCase(Locale.ROOT), R.drawable.canthiep)
        )
        listData.add(
            Question("cattuong".toUpperCase(Locale.ROOT), R.drawable.cattuong)
        )
        listData.add(
            Question("quyhang".toUpperCase(Locale.ROOT), R.drawable.quyhang)
        )

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_next -> {
                count++
                resetBtn()
                updateQuestionContent()
                updateText()
                deleteBtn()
                btn_next.visibility = View.INVISIBLE
            }
        }

    }

}
