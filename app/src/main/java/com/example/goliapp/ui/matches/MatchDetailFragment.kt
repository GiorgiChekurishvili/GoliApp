package com.example.goliapp.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.goliapp.R
import com.example.goliapp.databinding.FragmentMatchDetailBinding
import com.example.goliapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchDetailFragment : Fragment() {

    private var _binding: FragmentMatchDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MatchDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // უკან დაბრუნების ღილაკი (XML-ში გაქვს btn_back)
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.match.observe(viewLifecycleOwner) { result ->
            // თუ XML-ში დაამატებ ProgressBar-ს ID-ით progressBar, ეს ხაზი ჩართე:
            // binding.progressBar.visibility = if (result is Resource.Loading) View.VISIBLE else View.GONE

            if (result is Resource.Success && result.data != null) {
                val match = result.data
                binding.apply {
                    // 1. გუნდების სახელები (შენს Match.kt-ში არის homeTeamName და awayTeamName)
                    tvHomeName.text = match.homeTeamName
                    tvAwayName.text = match.awayTeamName

                    // 2. ანგარიში (ვიყენებთ Match.kt-ში არსებულ scoreDisplay-ს ან ხელით ვაწყობთ)
                    tvScore.text = match.scoreDisplay

                    // 3. ლიგა და სტატუსი (შენს Match.kt-ში არის leagueName და statusLong)
                    tvLeague.text = match.leagueName
                    tvStatus.text = match.statusLong

                    // 4. Match Info სექცია
                    tvMatchDate.text = match.date
                    tvMatchStatus.text = match.statusLong
                    // tvMatchRound.text = match.round // თუ XML-ში tv_match_round ბოლომდე გიწერია

                    // 5. სურათების ჩატვირთვა (Match.kt-ში არის homeTeamLogo და awayTeamLogo)
                    Glide.with(ivHomeLogo.context)
                        .load(match.homeTeamLogo)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(ivHomeLogo)

                    Glide.with(ivAwayLogo.context)
                        .load(match.awayTeamLogo)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(ivAwayLogo)
                }
            }
        }

        viewModel.isFavourite.observe(viewLifecycleOwner) { isFav ->
            // შეამოწმე რომ drawable-ებში ic_star_filled და ic_star_outline გაქვს
            binding.btnFavourite.setImageResource(
                if (isFav) R.drawable.ic_star_filled else R.drawable.ic_star_outline
            )
        }

        binding.btnFavourite.setOnClickListener { viewModel.toggleFavourite() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}