package com.example.comicslibrary.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.example.comicslibrary.SingletonHolder
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ConnectivityMonitor private constructor(context: Context): ConnectivityObservable {

    companion object: SingletonHolder<ConnectivityMonitor, Context>(:: ConnectivityMonitor)

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager

  //is the connectivity available or not, tha to epistrefoume sto call
    override fun observe(): Flow<ConnectivityObservable.Status> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                launch { send(ConnectivityObservable.Status.Available) }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                launch { send(ConnectivityObservable.Status.Unavailable) }
            }
        }

      connectivityManager.registerDefaultNetworkCallback(callback)
      if (connectivityManager.activeNetwork == null)
          launch { send(ConnectivityObservable.Status.Unavailable) }

          awaitClose {
              connectivityManager.unregisterNetworkCallback(callback)
          }
    }.distinctUntilChanged() //oxi pollapla status otan kanoume receive allo callback

}