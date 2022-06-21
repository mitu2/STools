package runstatic.stools.util

import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.util.concurrent.ThreadLocalRandom

/**
 *
 * @author chenmoand
 */
object VerifyCodeUtil {

    fun drawCodeToImage(
        image: BufferedImage /*= BufferedImage(200, 40, BufferedImage.TYPE_INT_RGB)*/,
        width: Int = image.width,
        height: Int = image.height,
        code: String = RandomStringUtil.randomString(4, 6)
    ): String {
        val graphics = image.graphics as Graphics2D
        //设置画笔颜色-验证码背景色
        graphics.color = Color.WHITE
        graphics.fillRect(0, 0, width, height) //填充背景
        graphics.font = Font("微软雅黑", Font.BOLD, 40)
        //数字和字母的组合
        var x = 10 //旋转原点的 x 坐标
        val random = ThreadLocalRandom.current()
        for (chr in code) {
            graphics.color = getRandomColor()
            //设置字体旋转角度
            val degree = random.nextInt() % 30 //角度小于30度
            //正向旋转
            graphics.rotate(degree * Math.PI / 180, x.toDouble(), 45.0)
            graphics.drawString(chr.toString(), x, 45)
            //反向旋转
            graphics.rotate(-degree * Math.PI / 180, x.toDouble(), 45.0)
            x += 48
        }

        //画干扰线
        for (i in 0..5) {
            // 设置随机颜色
            graphics.color = getRandomColor()
            // 随机画线
            graphics.drawLine(
                random.nextInt(width), random.nextInt(height),
                random.nextInt(width), random.nextInt(height)
            )
        }
        //添加噪点
        for (i in 0..29) {
            val x1 = random.nextInt(width)
            val y1 = random.nextInt(height)
            graphics.color = getRandomColor()
            graphics.fillRect(x1, y1, 2, 2)
        }
        return code
    }

    /**
     * 随机取色
     */
    private fun getRandomColor(): Color {
        val ran = ThreadLocalRandom.current()
        return Color(
            ran.nextInt(256),
            ran.nextInt(256),
            ran.nextInt(256)
        )
    }


}