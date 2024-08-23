
function DawnPackage(name) {
	var p = Packages.rhino.NativeJavaPackage(name, Vars.mods.mainLoader());
	Packages.rhino.ScriptRuntime.setObjectProtoAndParent(p, Vars.mods.scripts.scope)
	return p
}

//var DawnJavaLibPack = DawnPackage('dawnunitlib')
//importPackage(DawnJavaLibPack)
//importPackage(DawnJavaLibPack.devourspirit)
//importPackage(DawnJavaLibPack.lib)
//importPackage(DawnJavaLibPack.mod.type)
//importPackage(DawnJavaLibPack.mod.unit)

let cx = Seq([Packages.rhino.Context]).get(0);
let field = cx.getDeclaredField("applicationClassLoader");
field.setAccessible(true)
field.set(Vars.mods.getScripts().context, Vars.mods.mainLoader());