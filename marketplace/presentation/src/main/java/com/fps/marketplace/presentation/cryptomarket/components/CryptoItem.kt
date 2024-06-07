package com.fps.marketplace.presentation.cryptomarket.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.fps.core.presentation.designsystem.QryptiumDarkRed
import com.fps.core.presentation.designsystem.QryptiumLimeGreen
import com.fps.core.presentation.designsystem.QryptiumTheme
import com.fps.marketplace.presentation.R
import com.fps.marketplace.presentation.cryptomarket.models.CryptoPairUi
import kotlin.random.Random

@Composable
fun CryptoItem(
    cryptoPairUi: CryptoPairUi,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cryptoPairUi.image)
                    .memoryCacheKey(cryptoPairUi.image)
                    .diskCacheKey(cryptoPairUi.image)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .placeholder(com.fps.core.presentation.designsystem.R.drawable.crypto_placeholder)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .error(com.fps.core.presentation.designsystem.R.drawable.error_img)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Text(text = cryptoPairUi.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                // There was no yield information retrieved from the API, so in order to add this detail in the UI and to be
                // changed later, I've added this Random true/false
                if (Random.nextBoolean()) {
                    Surface(
                        modifier = Modifier
                            .width(70.dp)
                            .height(25.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(4.dp),
                        color = QryptiumLimeGreen // light green background
                    ) {
                        Text(
                            text = stringResource(id = R.string.earn_yield),
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Text(text = cryptoPairUi.price, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Row {
                    Icon(
                        imageVector = if (cryptoPairUi.isUptrend) Icons.Default.KeyboardArrowUp else
                            Icons.Default.KeyboardArrowDown,
                        tint = if (cryptoPairUi.isUptrend) QryptiumLimeGreen else QryptiumDarkRed,
                        contentDescription = "Search icon"
                    )
                    Text(
                        text = cryptoPairUi.change24h,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (cryptoPairUi.isUptrend) QryptiumLimeGreen else QryptiumDarkRed
                    )
                }

            }
        }
    }
}

@Preview
@Composable
private fun CryptoItemPreview() {
    QryptiumTheme {
        CryptoItem(
            cryptoPairUi = CryptoPairUi(
                name = "Btc",
                price = "10000",
                isUptrend = true,
                change24h = "5%"
            )
        )
    }
}