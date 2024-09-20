package com.erdemyesilcicek.handsoffate.view

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.erdemyesilcicek.handsoffate.R
import com.erdemyesilcicek.handsoffate.databinding.FragmentPlayWithComputerBinding
import com.erdemyesilcicek.handsoffate.databinding.FragmentPlayWithOtherBinding

class PlayWithComputerFragment : Fragment() {
    private var _binding: FragmentPlayWithComputerBinding? = null
    private val binding get() = _binding!!

    private var computerAnimation : AnimationDrawable? = null
    private var mainPlayerAnimation : AnimationDrawable? = null
    private var setTimer : CountDownTimer? = null

    private var allowPlaying = true

    private lateinit var mainPlayerSelection : String
    private lateinit var computerSelection : String

    private var scoreMainPlayer = 0
    private var scoreComputer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayWithComputerBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.computerFragmentMainPlayerButtonRock.setOnClickListener {
            onPlay("rock")
        }
        binding.computerFragmentMainPlayerButtonPaper.setOnClickListener {
            onPlay("paper")
        }
        binding.computerFragmentMainPlayerButtonScissor.setOnClickListener {
            onPlay("scissor")
        }
    }

    private fun playAnimation() {
        binding.computerFragmentMainPlayerIcon.setImageResource(0)
        binding.computerIcon.setImageResource(0)
        binding.computerFragmentMainPlayerStatus.text = ""
        binding.computerFragmentComputerStatus.text = ""

        binding.computerIcon.setBackgroundResource(R.drawable.animation_hands)
        computerAnimation = binding.computerIcon.background as AnimationDrawable

        binding.computerFragmentMainPlayerIcon.setBackgroundResource(R.drawable.animation_hands)
        mainPlayerAnimation = binding.computerFragmentMainPlayerIcon.background as AnimationDrawable

        setTimer = object : CountDownTimer(3000,1000) {
            override fun onTick(p0: Long) {
                computerAnimation?.start()
                mainPlayerAnimation?.start()
            }

            override fun onFinish() {
                computerAnimation?.stop()
                mainPlayerAnimation?.stop()

                allowPlaying = true

                binding.computerFragmentMainPlayerIcon.setImageResource(0)
                binding.computerIcon.setImageResource(0)

                setSelectionIcon()
                setScore()
                endGame()
            }
        }.start()
    }

    private fun onPlay(selection : String){
        if(allowPlaying){
            mainPlayerSelection = selection
            computerSelection = listOf("rock", "paper", "scissor").random()
            allowPlaying = false
            playAnimation()
        }
    }

    private fun setSelectionIcon(){
        when(mainPlayerSelection){
            "rock" -> binding.computerFragmentMainPlayerIcon.setImageResource(R.drawable.rock)
            "paper" -> binding.computerFragmentMainPlayerIcon.setImageResource(R.drawable.paper)
            "scissor" -> binding.computerFragmentMainPlayerIcon.setImageResource(R.drawable.scissor)
        }
        when(computerSelection){
            "rock" -> binding.computerIcon.setImageResource(R.drawable.rock)
            "paper" -> binding.computerIcon.setImageResource(R.drawable.paper)
            "scissor" -> binding.computerIcon.setImageResource(R.drawable.scissor)
        }
    }
    private fun getResult():String {
        return if (mainPlayerSelection == computerSelection)
            "tie"
        else if(mainPlayerSelection == "rock" && computerSelection == "scissor" || mainPlayerSelection == "paper" && computerSelection == "rock" ||
            mainPlayerSelection == "scissor" && computerSelection == "paper")
            "mainPlayer"
        else
            "secondPlayer"
    }
    private fun setScore(){
        if(getResult() == "tie"){
            binding.computerFragmentMainPlayerStatus.text = "Tie"
            binding.computerFragmentComputerStatus.text = "Tie"
        }
        else if(getResult() == "mainPlayer"){
            binding.computerFragmentMainPlayerStatus.text = "Win"
            binding.computerFragmentComputerStatus.text = "Lose"
            scoreMainPlayer++
            when(scoreMainPlayer){
                1 -> binding.computerFragmentMainPlayerFirstStar.setImageResource(R.drawable.star)
                2 -> binding.computerFragmentMainPlayerSecondStar.setImageResource(R.drawable.star)
                //3 -> binding.computerFragmentMainPlayerThirdStar.setImageResource(R.drawable.star)
                3 -> mainPlayerWin()
            }
        }
        else if(getResult() == "secondPlayer"){
            binding.computerFragmentMainPlayerStatus.text = "Lose"
            binding.computerFragmentComputerStatus.text = "Win"
            scoreComputer++
            when(scoreComputer){
                1 -> binding.computerFragmentComputerFirstStar.setImageResource(R.drawable.star)
                2 -> binding.computerFragmentComputerSecondStar.setImageResource(R.drawable.star)
                //3 -> binding.computerFragmentComputerThirdStar.setImageResource(R.drawable.star)
                3 -> comWin()
            }
        }
    }

    private fun endGame(){
        if(scoreMainPlayer == 3 || scoreComputer == 3){
            println("winwinwin")
            //val action = PlayWithOtherFragmentDirections.actionPlayWithOtherFragmentToFinishFragment()
            //Navigation.findNavController(requireView()).navigate(action)
        }
    }

    private fun mainPlayerWin(){
        binding.computerFragmentMainPlayerThirdStar.setImageResource(R.drawable.star)

        val action = PlayWithComputerFragmentDirections.actionPlayWithComputerFragmentToFinishFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun comWin(){
        binding.computerFragmentComputerThirdStar.setImageResource(R.drawable.star)

        val action = PlayWithComputerFragmentDirections.actionPlayWithComputerFragmentToComputerGameOverFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}