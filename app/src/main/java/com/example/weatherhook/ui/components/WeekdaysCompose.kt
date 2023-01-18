package com.example.weatherhook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


//@Preview
//@SuppressLint("SuspiciousIndentation")
@Composable
fun Weekdays(daysList: List<String>, size: Int) {
        val background = colorResource(id = R.color.white)
        val backgroundActivated = colorResource(id = R.color.dark_green)
        val borderRadius = (size/5).toInt()
        val textColor = colorResource(id = R.color.black_green)
        val textColorActivated = colorResource(id = R.color.white)
        var colorColor = Color.Black
        Row(modifier = Modifier.padding(10.dp), horizontalArrangement = Arrangement.spacedBy((size/4).dp)) {
            Surface(elevation = 3.dp, shape = RoundedCornerShape(borderRadius.dp)) {
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(size.dp)
                        .width(size.dp)
                        .then(
                            if (daysList.contains("MO"))
                                Modifier.background(backgroundActivated)
                            else Modifier
                                .background(background)
                        )
                ){

                    if (daysList.contains("MO")) colorColor = textColorActivated
                    else colorColor = textColor
                    Text(
                        text = "MO",
                        textAlign = TextAlign.Center,
                        color = colorColor,
                        fontSize = (size/3).sp,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

            Surface(elevation = 3.dp, shape = RoundedCornerShape(borderRadius.dp)) {
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(size.dp)
                        .width(size.dp)
                        .then(
                            if (daysList.contains("TU"))
                                Modifier.background(backgroundActivated)
                            else Modifier
                                .background(background)
                        )
                ){

                    if (daysList.contains("TU")) colorColor = textColorActivated
                    else colorColor = textColor
                    Text(
                        text = "TU",
                        textAlign = TextAlign.Center,
                        color = colorColor,
                        fontSize = (size/3).sp,
                        modifier = Modifier.padding(4.dp),
                    )
                }
            }

            Surface(elevation = 3.dp, shape = RoundedCornerShape(borderRadius.dp)) {
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(size.dp)
                        .width(size.dp)
                        .then(
                            if (daysList.contains("WE"))
                                Modifier.background(backgroundActivated)
                            else Modifier
                                .background(background)
                        )
                ){

                    if (daysList.contains("WE")) colorColor = textColorActivated
                    else colorColor = textColor
                    Text(
                        text = "WE",
                        textAlign = TextAlign.Center,
                        color = colorColor,
                        fontSize = (size/3).sp,
                        modifier = Modifier.padding(4.dp),
                    )
                }
            }

            Surface(elevation = 3.dp, shape = RoundedCornerShape(borderRadius.dp)) {
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(size.dp)
                        .width(size.dp)
                        .then(
                            if (daysList.contains("TH"))
                                Modifier.background(backgroundActivated)
                            else Modifier
                                .background(background)
                        )
                ){

                    if (daysList.contains("TH")) colorColor = textColorActivated
                    else colorColor = textColor
                    Text(
                        text = "TH",
                        textAlign = TextAlign.Center,
                        color = colorColor,
                        fontSize = (size/3).sp,
                        modifier = Modifier.padding(4.dp),
                    )
                }
            }

            Surface(elevation = 3.dp, shape = RoundedCornerShape(borderRadius.dp)) {
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(size.dp)
                        .width(size.dp)
                        .then(
                            if (daysList.contains("FR"))
                                Modifier.background(backgroundActivated)
                            else Modifier
                                .background(background)
                        )
                ){

                    if (daysList.contains("FR")) colorColor = textColorActivated
                    else colorColor = textColor
                    Text(
                        text = "FR",
                        textAlign = TextAlign.Center,
                        color = colorColor,
                        fontSize = (size/3).sp,
                        modifier = Modifier.padding(4.dp),
                    )
                }
            }

            Surface(elevation = 3.dp, shape = RoundedCornerShape(borderRadius.dp)) {
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(size.dp)
                        .width(size.dp)
                        .then(
                            if (daysList.contains("SA"))
                                Modifier.background(backgroundActivated)
                            else Modifier
                                .background(background)
                        )
                ){

                    if (daysList.contains("SA")) colorColor = textColorActivated
                    else colorColor = textColor
                    Text(
                        text = "SA",
                        textAlign = TextAlign.Center,
                        color = colorColor,
                        fontSize = (size/3).sp,
                        modifier = Modifier.padding(4.dp),
                    )
                }
            }

            Surface(elevation = 3.dp, shape = RoundedCornerShape(borderRadius.dp)) {
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(size.dp)
                        .width(size.dp)
                        .then(
                            if (daysList.contains("SU"))
                                Modifier.background(backgroundActivated)
                            else Modifier
                                .background(background)
                        )
                ){

                    if (daysList.contains("SU")) colorColor = textColorActivated
                    else colorColor = textColor
                    Text(
                        text = "SU",
                        textAlign = TextAlign.Center,
                        color = colorColor,
                        fontSize = (size/3).sp,
                        modifier = Modifier.padding(4.dp),
                    )
                }
            }

        }
}

