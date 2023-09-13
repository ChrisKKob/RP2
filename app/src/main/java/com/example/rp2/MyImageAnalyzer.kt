package com.example.rp2

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class MyImageAnalyzer : ImageAnalysis.Analyzer {

    override fun analyze(imageProxy: ImageProxy) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        val mediaImage = imageProxy.image
        if(mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            val result = recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    print("Imagem analisada com sucesso")

                    val resultText = visionText.text
                    for (block in visionText.textBlocks) {
                        val blockText = block.text
                        val blockCornerPoints = block.cornerPoints
                        val blockFrame = block.boundingBox
                        print(blockText)
                        print(blockCornerPoints)
                        print(blockFrame)
                        for (line in block.lines) {
                            val lineText = line.text
                            val lineCornerPoints = line.cornerPoints
                            val lineFrame = line.boundingBox
                            print(lineText)
                            print(lineCornerPoints)
                            print(lineFrame)
                            for (element in line.elements) {
                                val elementText = element.text
                                val elementCornerPoints = element.cornerPoints
                                val elementFrame = element.boundingBox
                                print(elementText)
                                print(elementCornerPoints)
                                print(elementFrame)
                            }
                        }
                    }

                }
                .addOnFailureListener { e ->
                    print("Erro ao analisar imagem")
                }
        }

    }
}