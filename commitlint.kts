import kotlin.system.exitProcess
import java.io.File

fun error(message: String) {
    println("[CommitLint] ${message}")
    exitProcess(1)
}

if (args.size == 0) {
    error("No file provided")
}

val commitMessageFile = File(args[0])
if (!commitMessageFile.exists()) {
    error("Argument is not a commit message")
}

val lines = commitMessageFile
        .readLines()
        .filter { !it.startsWith("#") }
        .map { it.trim() }

if (lines.isEmpty()) {
    error("There is no commit message")
}

val subjectRegex = Regex("((build|ci|docs|feat|fix|perf|refactor|style|test)\\(.+\\):\\s(.+))")
val subject = lines[0]
if (subject.length > 72) {
    error("Subject line is too long")
}

if (!subjectRegex.matches(subject)) {
    error("Subject line does not match the rules")
}

if (lines.size > 2) {
    if (lines[1].isNotEmpty()) {
        error("Second line should be empty")
    }
}

exitProcess(0)