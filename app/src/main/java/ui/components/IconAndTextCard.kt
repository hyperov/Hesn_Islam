package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.islam.hesn.myapplication.R
import ui.theme.ColorAccent
import ui.theme.ColorPrimary
import ui.theme.JanaFamily

@Composable
fun IconAndTextCard(
    modifier: Modifier,
    imageVector: ImageVector,
    textResource: String,
    onClick: () -> Unit,) {
    Card(
        modifier = modifier
            .width(200.dp)
            .height(180.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = ColorAccent,
        ),
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = imageVector,
                contentDescription = null,
                modifier = modifier.size(100.dp)
            )
            Spacer(modifier)
            Text(
                text = textResource,
                fontSize = 24.sp, fontFamily = JanaFamily,
                color = ColorPrimary
            )
        }
    }
}

@Preview
@Composable
fun IconAndTextCardPreview() {
    IconAndTextCard(modifier = Modifier,
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_bookmark2),
        textResource = stringResource(R.string.last_read),
        onClick = {})
}