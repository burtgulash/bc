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
				out.write(r"\begin{center}")
				out.write("\n")
				out.write(r"\begin{tabular}{|l|l|c|c|c|c|c|}")
				out.write("\n")
				out.write(r"\hline")
				out.write("\n")
				out.write(r"& {\bf Autor} & {\bf METODA} & {\bf Turing} & {\bf Codd} & {\bf Fellows} & {\bf ISI} \\".replace("METODA", just_name))
				out.write("\n")
				out.write(r"\hline")

				out.write("\n")
				i = 0
				for line in f:
					i += 1
					line = str(i) + " & " + line
					line = line.replace(";", " & ")
					line = line.replace(" x ", r" $\bullet$ ")
					line = line.replace(" x", r" $\bullet$")
					line = line.replace("\n", " \\\\\n\\hline\n")
					out.write(line)
				out.write(r"\end{tabular}")
				out.write("\n")
				out.write(r"\end{center}")
