<?php

use Illuminate\Http\Request; 
use Illuminate\Support\Facades\Route;
use Illuminate\Support\Facades\DB;
use App\Http\Controllers\RegistroController;
use App\Http\Controllers\FormularioController;

Route::get('/', function () {
    return view('inicio');
});

Route::get('/registro', function () {
    return view('registro');
});

Route::post('/registro', [RegistroController::class, 'registro'])->name('registro');


Route::get('/formulario', function () {
    return view('formulario');
});

Route::post('/formulario', [FormularioController::class, 'guardar'])->name('formulario.guardar');



Route::get('/dashboardLogin', function () {
    return view('dashboardLogin');
});

Route::get('/dashboardLogin', [FormularioController::class, 'mostrarDashboardLogin'])->name('dashboardLogin');


Route::get('/dashboardHfijo', function () {
    return view('dashboardHfijo');
});



Route::get('/dashboardHfijo', [FormularioController::class, 'mostrarDashboardHfijo'])->name('dashboardHfijo');

Route::post('/dashboardLogin', [FormularioController::class, 'actualizarFormulario'])->name('formulario.actualizarFormulario');

// Ruta para procesar el login y redireccionar al dashboard
Route::post('/inicio', function (Request $request) {
    $usuario = $request->input('usuario');
    $contrasena = $request->input('password');

    $usuarioExistente = DB::table('tabla_login')
        ->where('usuario', $usuario)
        ->where('contrasena', $contrasena)
        ->first();

    if ($usuarioExistente) {
        // Autenticación exitosa, capturar el ID del usuario logueado
        $userId = $usuarioExistente->id;

          // Almacenar el ID del usuario en la sesión
          session(['userId' => $userId]);
        
        // Redireccionar al dashboard
        return redirect('/dashboardLogin');
    } else {
        // Credenciales inválidas, mostrar mensaje de error
        return "Credenciales inválidas";
    }
})->name('inicio');



