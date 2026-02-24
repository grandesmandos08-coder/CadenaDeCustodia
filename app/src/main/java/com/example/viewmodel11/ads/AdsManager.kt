package com.example.viewmodel11.ads

import android.app.Activity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

    class AdsManager(
        private val activity: Activity,
        private val adUnitId: String
    ) {

        private var interstitialAd: InterstitialAd? = null

        init {
            loadAd()
        }

        private fun loadAd() {
            val request = AdRequest.Builder().build()

            InterstitialAd.load(
                activity,
                adUnitId,
                request,
                object : InterstitialAdLoadCallback() {

                    override fun onAdLoaded(ad: InterstitialAd) {
                        interstitialAd = ad
                    }

                    override fun onAdFailedToLoad(error: LoadAdError) {
                        interstitialAd = null
                    }
                }
            )
        }

        fun isAdLoaded(): Boolean {
            return interstitialAd != null
        }

        fun showAd(onFinish: () -> Unit) {

            val ad = interstitialAd

            if (ad != null) {

                ad.fullScreenContentCallback = object : FullScreenContentCallback() {

                    override fun onAdDismissedFullScreenContent() {
                        interstitialAd = null
                        loadAd()
                        onFinish()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        interstitialAd = null
                        loadAd()
                        onFinish()
                    }
                }

                ad.show(activity)

            } else {
                onFinish()
            }
        }
    }