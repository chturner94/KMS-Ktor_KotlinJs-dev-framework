import io.kotest.core.config.AbstractProjectConfig
//Guide: https://proandroiddev.com/kotlintest-pro-tips-54ce19c32c68

class ProjectConfig: AbstractProjectConfig() {
    override val globalAssertSoftly: Boolean = true
    override val failOnIgnoredTests: Boolean = false
    override val parallelism: Int = 5




}


// Useful in test options
// ----------------------
// To randomise the order of test for a single instance of a test class use:
//
// class RandomSpec: StringSpec() {
//      override fun testCaseOrder() = TestCaseOrder.Random
//          init {
//                // tests here
//          }
// }
//