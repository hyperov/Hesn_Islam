package com.islam.hesn.myapplication.misconceptions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.islam.hesn.myapplication.R
import ui.components.IconAndTextCard
import ui.theme.ColorAccent
import ui.theme.ColorPrimary
import ui.theme.ColorPrimaryDark
import ui.theme.JanaFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisconceptionsMainScreen(
    modifier: Modifier,
    navigateToMisconceptions: () -> Unit,
    navigateToPdf: () -> Unit,
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.misconceptions_main_title),
                            fontFamily = JanaFamily,
                            fontSize = 24.sp,
                            color = ColorAccent,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ColorPrimary,
                    titleContentColor = ColorAccent,
                )
            )

        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = ColorPrimaryDark),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconAndTextCard(
                modifier = Modifier,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_reading_book),
                textResource = stringResource(R.string.misconceptions),
                onClick = {
                    navigateToMisconceptions()
                })
            IconAndTextCard(
                modifier = Modifier,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_religion),
                textResource = stringResource(R.string.book),
                onClick = {
                    navigateToPdf()
                })
        }
    }
}