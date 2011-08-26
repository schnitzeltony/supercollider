WindowsPlatform : Platform
{
	name { ^\windows }
	startupFiles {
		var deprecated = ["startup.sc", "~\\SuperCollider\\startup.sc".standardizePath];
		Platform.deprecatedStartupFiles(deprecated);
		^(deprecated ++ super.startupFiles)
	}

	startup {
		// Server setup
		Server.program = "scsynth.exe";

		// Score setup
		Score.program = Server.program;

		// load user startup file
		this.loadStartupFiles;
	}

	defaultHIDScheme { ^nil }

	pathSeparator { ^$\\ }
	isPathSeparator { |char|
		^#[$\\, $/].includes(char)
	}
	clearMetadata { |path|
		path = path.splitext[0].do({ |chr, i| if(chr == $/) { path[i] = $\\.asAscii } });
		"del %%.*meta%".format(34.asAscii, path, 34.asAscii).systemCmd;
	}

	killAll { |cmdLineArgs|
		("taskkill /F /IM " ++ cmdLineArgs).unixCmd;
	}
}
