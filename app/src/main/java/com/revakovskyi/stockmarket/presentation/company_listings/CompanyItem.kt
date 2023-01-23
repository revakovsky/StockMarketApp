package com.revakovskyi.stockmarket.presentation.company_listings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.revakovskyi.stockmarket.domain.model.CompanyListing

@Composable
fun CompanyItem(
    company: CompanyListing,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = company.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = company.exchange,
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp,
                    color = Color.White,
                )
            }
            Spacer(modifier = Modifier.heightIn(8.dp))
            Text(
                text = "(${company.symbol})",
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                color = Color.White,
            )
        }
    }
}