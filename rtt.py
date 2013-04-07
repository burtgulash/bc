#!/usr/bin/env python

import os, sys, shutil

if __name__ == "__main__":
	if len(sys.argv) == 2:
		path = sys.argv[1]
		tabels = path + "/tabels"
		shutil.rmtree(tabels)
		os.mkdir(tabels)
		for fname in os.listdir(path):
			just_name = fname.replace(".csv", "")
			out = open(tabels + "/" + just_name + ".tex", "w")
			fullname = path + "/" + fname
			if os.path.isdir(fullname):
				continue
			print fullname
			with open(fullname) as f:
				next(f)
				out.write(r"""
\begin{table}[!ht]
\begin{center}
\makebox[\linewidth]{
\begin{small}
\begin{tabular}{c|c|c|c|c|c|c}
\toprule
""")
				out.write(r"& {\bf Autor} & {\bf METODA} & {\bf Turing} & {\bf Codd} & {\bf Fellows} & {\bf ISI} \\".replace("METODA", just_name))
				out.write("\n")
				out.write(r"\midrule")

				out.write("\n")
				i = 0
				for line in f:
					i += 1
					if i > 1:
						out.write(r"\hline")
						out.write("\n")
					line = str(i) + " & " + line
					line = line.replace(";", " & ")
					line = line.replace(" x ", r" $\bullet$ ")
					line = line.replace(" x", r" $\bullet$")
					line = line.replace("\n", " \\\\\n")
					out.write(line)
				out.write(r"""
\bottomrule
\end{tabular}
\end{small}
}
\end{center}
\end{table}
""")
