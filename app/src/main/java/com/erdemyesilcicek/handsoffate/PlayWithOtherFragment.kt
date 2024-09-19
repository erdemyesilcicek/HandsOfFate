package com.erdemyesilcicek.handsoffate

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Selection
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.erdemyesilcicek.handsoffate.databinding.FragmentMenuBinding
import com.erdemyesilcicek.handsoffate.databinding.FragmentPlayWithOtherBinding

class PlayWithOtherFragment : Fragment() {

    private var _binding: FragmentPlayWithOtherBinding? = null
    private val binding get() = _binding!!

    private var secondPlayerAnimation : AnimationDrawable? = null
    private var mainPlayerAnimation : AnimationDrawable? = null
    private var setTimer : CountDownTimer? = null

    private var mainPlayerReady = false
    private var secondPlayerReady = false
    private var allowPlaying = true

    private lateinit var mainPlayerSelection : String
    private lateinit var secondPlayerSelection : String

    private var scoreMainPlayer = 0
    private var scoreSecondPlayer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayWithOtherBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonsClicked()
    }
    private fun buttonsClicked(){
        binding.MainPlayerButtonRock.setOnClickListener {
            onPlayMainPlayer("rock")
        }
        binding.MainPlayerButtonPaper.setOnClickListener {
            onPlayMainPlayer("paper")
        }
        binding.MainPlayerButtonScissor.setOnClickListener {
            onPlayMainPlayer("scissor")
        }

        binding.SecondPlayerButtonRock.setOnClickListener {
            onPlaySecondPlayer("rock")
        }
        binding.SecondPlayerButtonPaper.setOnClickListener {
            onPlaySecondPlayer("paper")
        }
        binding.SecondPlayerButtonScissor.setOnClickListener {
            onPlaySecondPlayer("scissor")
        }
    }

    private fun playAnimation() {
        binding.mainPlayerIcon.setImageResource(0)
        binding.secondPlayerIcon.setImageResource(0)
        binding.mainPlayerStatus.text = ""
        binding.secondPlayerStatus.text = ""

        binding.secondPlayerIcon.setBackgroundResource(R.drawable.animation_hands)
        secondPlayerAnimation = binding.secondPlayerIcon.background as AnimationDrawable

        binding.mainPlayerIcon.setBackgroundResource(R.drawable.animation_hands)
        mainPlayerAnimation = binding.mainPlayerIcon.background as AnimationDrawable

        setTimer = object : CountDownTimer(3000,1000){
            override fun onTick(p0: Long) {
                secondPlayerAnimation?.start()
                mainPlayerAnimation?.start()
            }

            override fun onFinish() {
                secondPlayerAnimation?.stop()
                mainPlayerAnimation?.stop()

                allowPlaying = true
                mainPlayerReady = false
                secondPlayerReady = false

                binding.mainPlayerIcon.setImageResource(0)
                binding.secondPlayerIcon.setImageResource(0)

                setSelectionIcon()
                setScore()
            }
        }.start()
    }
    private fun onPlayMainPlayer(selection: String){
        if(allowPlaying){
            binding.mainPlayerIcon.setImageResource(R.drawable.check)
            binding.mainPlayerStatus.text = "Ready"
            mainPlayerSelection = selection
            mainPlayerReady = true

            if(secondPlayerReady){
                allowPlaying = false
                playAnimation()
            }
        }
    }
    private fun onPlaySecondPlayer(selection: String){
        if(allowPlaying){
            binding.secondPlayerIcon.setImageResource(R.drawable.check)
            binding.secondPlayerStatus.text = "Ready"
            secondPlayerSelection = selection
            secondPlayerReady = true

            if(mainPlayerReady){
                allowPlaying = false
                playAnimation()
            }
        }
    }
    private fun setSelectionIcon(){
        when(mainPlayerSelection){
            "rock" -> binding.mainPlayerIcon.setImageResource(R.drawable.rock)
            "paper" -> binding.mainPlayerIcon.setImageResource(R.drawable.paper)
            "scissor" -> binding.mainPlayerIcon.setImageResource(R.drawable.scissor)
        }
        when(secondPlayerSelection){
            "rock" -> binding.secondPlayerIcon.setImageResource(R.drawable.rock)
            "paper" -> binding.secondPlayerIcon.setImageResource(R.drawable.paper)
            "scissor" -> binding.secondPlayerIcon.setImageResource(R.drawable.scissor)
        }
    }
    private fun getResult():String {
        return if (mainPlayerSelection == secondPlayerSelection)
            "tie"
        else if(mainPlayerSelection == "rock" && secondPlayerSelection == "scissor" || mainPlayerSelection == "paper" && secondPlayerSelection == "rock" ||
            mainPlayerSelection == "scissor" && secondPlayerSelection == "paper")
            "mainPlayer"
        else
            "secondPlayer"
    }
    private fun setScore(){
        if(getResult() == "tie"){
            binding.mainPlayerStatus.text = "Tie"
            binding.secondPlayerStatus.text = "Tie"
        }
        else if(getResult() == "mainPlayer"){
            binding.mainPlayerStatus.text = "Win"
            binding.secondPlayerStatus.text = "Lose"
            scoreMainPlayer++
            when(scoreMainPlayer){
                1 -> binding.MainPlayerFirstStar.setImageResource(R.drawable.star)
                2 -> binding.MainPlayerSecondStar.setImageResource(R.drawable.star)
                3 -> binding.MainPlayerThirdStar.setImageResource(R.drawable.star)
            }
        }
        else if(getResult() == "secondPlayer"){
            binding.mainPlayerStatus.text = "Lose"
            binding.secondPlayerStatus.text = "Win"
            scoreSecondPlayer++
            when(scoreSecondPlayer){
                1 -> binding.SecondPlayerFirstStar.setImageResource(R.drawable.star)
                2 -> binding.SecondPlayerSecondStar.setImageResource(R.drawable.star)
                3 -> binding.SecondPlayerThirdStar.setImageResource(R.drawable.star)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        setTimer = null
    }
}