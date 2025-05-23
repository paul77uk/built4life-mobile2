package com.built4life.built4life2.ui.screen.daily

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsIgnoringVisibility
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.built4life.built4life2.ui.screen.daily.days.friday.FridayScreen
import com.built4life.built4life2.ui.screen.daily.days.monday.MondayScreen
import com.built4life.built4life2.ui.screen.daily.days.saturday.SaturdayScreen
import com.built4life.built4life2.ui.screen.daily.days.sunday.SundayScreen
import com.built4life.built4life2.ui.screen.daily.days.thursday.ThursdayScreen
import com.built4life.built4life2.ui.screen.daily.days.tuesday.TuesdayScreen
import com.built4life.built4life2.ui.screen.daily.days.wednesday.WednesdayScreen
import com.built4life.built4life2.ui.viewmodel.WorkoutViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DailyScreen(
    workoutViewModel: WorkoutViewModel
) {
    val days =  listOf("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY")
    val date = LocalDate.now().dayOfWeek.toString()
    val pagerState =
        rememberPagerState(initialPage = days.indexOf(date)) { days.size }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        contentWindowInsets = WindowInsets.systemBarsIgnoringVisibility,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
//                        modifier = Modifier.fillMaxWidth().padding(top = 30.dp)
                    )  {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Next Page",
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    coroutineScope.launch {
                                        if (pagerState.currentPage == 0)
                                            pagerState.scrollToPage(days.size - 1)
                                        else
                                            pagerState.scrollToPage(pagerState.currentPage - 1)
                                    }
                                }
                        )
                        Text(
                            text = days[pagerState.currentPage],
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .width(200.dp)
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next Page",
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    coroutineScope.launch {
                                        if (pagerState.currentPage == days.size - 1)
                                            pagerState.scrollToPage(0)

                                        else
                                            pagerState.scrollToPage(pagerState.currentPage + 1)
                                    }
                                }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                )
            )
        },
    ) { contentPadding ->
        Box(
            modifier = Modifier.padding(contentPadding),
        ) {
            HorizontalPager(
                state = pagerState,
            ) { page ->
                when (page) {
                    0 -> MondayScreen(workoutViewModel)
                    1 -> TuesdayScreen(workoutViewModel)
                    2 -> WednesdayScreen(workoutViewModel)
                    3 -> ThursdayScreen(workoutViewModel)
                    4 -> FridayScreen(workoutViewModel)
                    5 -> SaturdayScreen(workoutViewModel)
                    6 -> SundayScreen(workoutViewModel)
                }
            }
        }
    }
}