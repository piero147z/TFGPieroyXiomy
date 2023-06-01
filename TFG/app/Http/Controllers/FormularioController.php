<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Formulario;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;


class FormularioController extends Controller
{
    //
    public function guardar(Request $request)
    {
    $formulario = new Formulario;
    $formulario->precio_vivienda = $request->input('precio_vivienda');
    $formulario->dinero_necesita = $request->input('monto_necesario');
    $formulario->años_pago = $request->input('anos_hipoteca');
    $formulario->Tipo_vivienda = $request->input('tipo_vivienda');
    $formulario->Uso_vivienda = $request->input('uso_vivienda');
    $formulario->provincia = $request->input('provincia');
    $formulario->edad = $request->input('edad');
    $formulario->Tipo_banco = $request->input('banco');
    $formulario->save();


 
    return redirect('/dashboardHfijo')->with('success', 'El formulario ha sido guardado exitosamente.');
    }

    public function mostrarDashboardHfijo(Request $request)
    {
        //////// Consultas Automatizadas de la tabla Hipoteca con Procesa Scraping////////////
        // Ejecutar la consulta SQL para obtener el primer mínimo
        $result = DB::select('SELECT BANCO, TIN, TAE  FROM hipoteca_fija where TIN = (select min(TIN) from hipoteca_fija) order by id DESC LIMIT 1');
    
        // Obtener el primer mínimo de la consulta
        $banco = $result[0]->BANCO;
        $mintin = $result[0]->TIN;
        $tae = $result[0]->TAE;
    
        // Ejecutar la consulta SQL para obtener el segundo mínimo
        $result = DB::select('SELECT BANCO, TIN, TAE  FROM hipoteca_fija where TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fija) order by id DESC LIMIT 1');
    
        // Obtener el segundo mínimo de la consulta
        $banco2 = $result[0]->BANCO;
        $mintin2 = $result[0]->TIN;
        $tae2 = $result[0]->TAE;
    
        // Ejecutar la consulta SQL para obtener el tercer mínimo
        $result = DB::select('SELECT BANCO, TIN, TAE FROM hipoteca_fija where TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fija) AND TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fija WHERE TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fija)) order by id DESC LIMIT 1');
    
        // Obtener el tercer mínimo de la consulta
        $banco3 = $result[0]->BANCO;
        $mintin3 = $result[0]->TIN;
        $tae3 = $result[0]->TAE;
    

        //////Calcular mejor Oferta///////////

        // Obtener el último registro en la tabla de formularios guardados
        $formulario = Formulario::latest()->first();

        // Obtener el valor de dinero_necesita desde el formulario obtenido
        $dineronecesita = $formulario->dinero_necesita;
        $amPrestamo = $dineronecesita; // Monto del préstamo

        // Obtener el valor de años_pago desde el formulario obtenido
        $añoHipoteca = $formulario->años_pago;
        $plazoEnMeses = $añoHipoteca * 12;

        // Cálculo de la cuota mensual
         $cuotaMensual = ($amPrestamo * ($mintin / 12)) / (1 - pow(1 + ($mintin / 12), -$plazoEnMeses));
         $cuotaMensual = round($cuotaMensual, 2);
         $cuotaMensualFormateada = number_format($cuotaMensual, 2, ',', '.') . ' €';

         $cuotaMensual2 = ($amPrestamo * ($mintin2 / 12)) / (1 - pow(1 + ($mintin2 / 12), -$plazoEnMeses));
         $cuotaMensual2 = round($cuotaMensual2, 2);
         $cuotaMensualFormateada2 = number_format($cuotaMensual2, 2, ',', '.') . ' €';


         $cuotaMensual3 = ($amPrestamo * ($mintin3 / 12)) / (1 - pow(1 + ($mintin3 / 12), -$plazoEnMeses));
         $cuotaMensual3 = round($cuotaMensual3, 2);
         $cuotaMensualFormateada3 = number_format($cuotaMensual3, 2, ',', '.') . ' €';

         //Calculo del % de pago 

         $totalInterest = ($cuotaMensual * $plazoEnMeses) - $amPrestamo;
         $capitalPaid = $amPrestamo - $totalInterest;
         $capitalPercentage1 = ($capitalPaid/$cuotaMensual)* 0.1;
         $capitalPercentage1 = round($capitalPercentage1, 2);
         $capitalPercentage1Formatted = $capitalPercentage1 . '%';

         $totalInterest2 = ($cuotaMensual2 * $plazoEnMeses) - $amPrestamo;
         $capitalPaid2 = $amPrestamo - $totalInterest2;
         $capitalPercentage2 = ($capitalPaid2/$cuotaMensual2)* 0.1;
         $capitalPercentage2 = round($capitalPercentage2, 2);
         $capitalPercentage1Formatted2 = $capitalPercentage2 . '%';

         $totalInterest3 = ($cuotaMensual3 * $plazoEnMeses) - $amPrestamo;
         $capitalPaid3 = $amPrestamo - $totalInterest3;
         $capitalPercentage3 = ($capitalPaid3/$cuotaMensual3)* 0.1;
         $capitalPercentage3 = round($capitalPercentage3, 2);
         $capitalPercentage1Formatted3 = $capitalPercentage3 . '%';

         //TIN %
         $mintinP= ($mintin*100);
         $mintinP2= ($mintin2*100);
         $mintinP3= ($mintin3*100);

         $taeP= ($tae*100);
         $taeP2= ($tae2*100);
         $taeP3= ($tae3*100);
 
    
        return view('dashboardHfijo', compact('cuotaMensualFormateada','cuotaMensual','cuotaMensual2','cuotaMensual3',
        'banco','banco2','banco3','mintinP','mintinP2','mintinP3','capitalPercentage1','capitalPercentage2','capitalPercentage3',
        'taeP','taeP2','taeP3'));
    }
    

    
    public function mostrarDashboardLogin()
    {
       // Obtener el ID del usuario logueado
       $userId = session('userId');

       // Obtener el valor de 'dinero_necesita' desde la tabla 'formulario' usando el ID del usuario
       $formulario = DB::table('formulario')->where('id', $userId)->first();


       //////// Consultas Automatizadas de la tabla Hipoteca con Procesa Scraping////////////
       // Ejecutar la consulta SQL para obtener el primer mínimo
       $result = DB::select('SELECT BANCO, TIN, TAE  FROM hipoteca_fija where TIN = (select min(TIN) from hipoteca_fija) order by id DESC LIMIT 1');
   
       // Obtener el primer mínimo de la consulta
       $banco = $result[0]->BANCO;
       $mintin = $result[0]->TIN;
       $tae = $result[0]->TAE;
   
       // Ejecutar la consulta SQL para obtener el segundo mínimo
       $result = DB::select('SELECT BANCO, TIN, TAE  FROM hipoteca_fija where TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fija) order by id DESC LIMIT 1');
   
       // Obtener el segundo mínimo de la consulta
       $banco2 = $result[0]->BANCO;
       $mintin2 = $result[0]->TIN;
       $tae2 = $result[0]->TAE;
   
       // Ejecutar la consulta SQL para obtener el tercer mínimo
       $result = DB::select('SELECT BANCO, TIN, TAE FROM hipoteca_fija where TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fija) AND TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fija WHERE TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fija)) order by id DESC LIMIT 1');
   
       // Obtener el tercer mínimo de la consulta
       $banco3 = $result[0]->BANCO;
       $mintin3 = $result[0]->TIN;
       $tae3 = $result[0]->TAE;
   

       //////Calcular mejor Oferta///////////

       // Obtener el último registro en la tabla de formularios guardados
       $formulario = DB::table('formulario')->where('id', $userId)->first();
    
       // Obtener el valor de dinero_necesita desde el formulario obtenido
       $dineronecesita = $formulario->dinero_necesita;
       $amPrestamo = $dineronecesita; // Monto del préstamo

       // Obtener el valor de años_pago desde el formulario obtenido
       $añoHipoteca = $formulario->años_pago;
       $plazoEnMeses = $añoHipoteca * 12;

       // Cálculo de la cuota mensual
        $cuotaMensual = ($amPrestamo * ($mintin / 12)) / (1 - pow(1 + ($mintin / 12), -$plazoEnMeses));
        $cuotaMensual = round($cuotaMensual, 2);
        $cuotaMensualFormateada = number_format($cuotaMensual, 2, ',', '.') . ' €';

        $cuotaMensual2 = ($amPrestamo * ($mintin2 / 12)) / (1 - pow(1 + ($mintin2 / 12), -$plazoEnMeses));
        $cuotaMensual2 = round($cuotaMensual2, 2);
        $cuotaMensualFormateada2 = number_format($cuotaMensual2, 2, ',', '.') . ' €';


        $cuotaMensual3 = ($amPrestamo * ($mintin3 / 12)) / (1 - pow(1 + ($mintin3 / 12), -$plazoEnMeses));
        $cuotaMensual3 = round($cuotaMensual3, 2);
        $cuotaMensualFormateada3 = number_format($cuotaMensual3, 2, ',', '.') . ' €';

        //Calculo del % de pago 

        $totalInterest = ($cuotaMensual * $plazoEnMeses) - $amPrestamo;
        $capitalPaid = $amPrestamo - $totalInterest;
        $capitalPercentage1 = ($capitalPaid/$cuotaMensual)* 0.1;
        $capitalPercentage1 = round($capitalPercentage1, 2);
        $capitalPercentage1Formatted = $capitalPercentage1 . '%';

        $totalInterest2 = ($cuotaMensual2 * $plazoEnMeses) - $amPrestamo;
        $capitalPaid2 = $amPrestamo - $totalInterest2;
        $capitalPercentage2 = ($capitalPaid2/$cuotaMensual2)* 0.1;
        $capitalPercentage2 = round($capitalPercentage2, 2);
        $capitalPercentage1Formatted2 = $capitalPercentage2 . '%';

        $totalInterest3 = ($cuotaMensual3 * $plazoEnMeses) - $amPrestamo;
        $capitalPaid3 = $amPrestamo - $totalInterest3;
        $capitalPercentage3 = ($capitalPaid3/$cuotaMensual3)* 0.1;
        $capitalPercentage3 = round($capitalPercentage3, 2);
        $capitalPercentage1Formatted3 = $capitalPercentage3 . '%';

        //TIN %
        $mintinP= ($mintin*100);
        $mintinP2= ($mintin2*100);
        $mintinP3= ($mintin3*100);

        $taeP= ($tae*100);
        $taeP2= ($tae2*100);
        $taeP3= ($tae3*100);

   
       return view('dashboardLogin', compact('cuotaMensualFormateada','cuotaMensual','cuotaMensual2','cuotaMensual3',
       'banco','banco2','banco3','mintinP','mintinP2','mintinP3','capitalPercentage1','capitalPercentage2','capitalPercentage3',
       'taeP','taeP2','taeP3'));
      
    }
    
    public function actualizarFormulario(Request $request)
    {
        // Obtener el ID del usuario logueado
        $userId = session('userId');
        
        // Obtener el formulario del usuario
        $formulario = Formulario::find($userId);
        
        if (!$formulario) {
            // El formulario no existe para el usuario dado, puedes manejar esto como desees
            return redirect()->back()->withErrors('No se encontró el formulario');
        }
        
        $dineroNecesita = $request->input('dinero_necesita');
        $añosPago = $request->input('años_pago');
        
        // Actualizar los valores del formulario
        $formulario->dinero_necesita = $dineroNecesita;
        $formulario->años_pago = $añosPago;
        $formulario->save();

          // Obtener el valor de 'dinero_necesita' desde la tabla 'formulario' usando el ID del usuario
       $formulario = DB::table('formulario')->where('id', $userId)->first();


       //////// Consultas Automatizadas de la tabla Hipoteca con Procesa Scraping////////////
       // Ejecutar la consulta SQL para obtener el primer mínimo
       $result = DB::select('SELECT BANCO, TIN, TAE  FROM hipoteca_fija where TIN = (select min(TIN) from hipoteca_fija) order by id DESC LIMIT 1');
   
       // Obtener el primer mínimo de la consulta
       $banco = $result[0]->BANCO;
       $mintin = $result[0]->TIN;
       $tae = $result[0]->TAE;
   
       // Ejecutar la consulta SQL para obtener el segundo mínimo
       $result = DB::select('SELECT BANCO, TIN, TAE  FROM hipoteca_fija where TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fija) order by id DESC LIMIT 1');
   
       // Obtener el segundo mínimo de la consulta
       $banco2 = $result[0]->BANCO;
       $mintin2 = $result[0]->TIN;
       $tae2 = $result[0]->TAE;
   
       // Ejecutar la consulta SQL para obtener el tercer mínimo
       $result = DB::select('SELECT BANCO, TIN, TAE FROM hipoteca_fija where TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fija) AND TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fija WHERE TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fija)) order by id DESC LIMIT 1');
   
       // Obtener el tercer mínimo de la consulta
       $banco3 = $result[0]->BANCO;
       $mintin3 = $result[0]->TIN;
       $tae3 = $result[0]->TAE;
   

       //////Calcular mejor Oferta///////////

       // Obtener el último registro en la tabla de formularios guardados
       $formulario = DB::table('formulario')->where('id', $userId)->first();
    
       // Obtener el valor de dinero_necesita desde el formulario obtenido
       $dineronecesita = $formulario->dinero_necesita;
       $amPrestamo = $dineronecesita; // Monto del préstamo

       // Obtener el valor de años_pago desde el formulario obtenido
       $añoHipoteca = $formulario->años_pago;
       $plazoEnMeses = $añoHipoteca * 12;

       // Cálculo de la cuota mensual
        $cuotaMensual = ($amPrestamo * ($mintin / 12)) / (1 - pow(1 + ($mintin / 12), -$plazoEnMeses));
        $cuotaMensual = round($cuotaMensual, 2);
        $cuotaMensualFormateada = number_format($cuotaMensual, 2, ',', '.') . ' €';

        $cuotaMensual2 = ($amPrestamo * ($mintin2 / 12)) / (1 - pow(1 + ($mintin2 / 12), -$plazoEnMeses));
        $cuotaMensual2 = round($cuotaMensual2, 2);
        $cuotaMensualFormateada2 = number_format($cuotaMensual2, 2, ',', '.') . ' €';


        $cuotaMensual3 = ($amPrestamo * ($mintin3 / 12)) / (1 - pow(1 + ($mintin3 / 12), -$plazoEnMeses));
        $cuotaMensual3 = round($cuotaMensual3, 2);
        $cuotaMensualFormateada3 = number_format($cuotaMensual3, 2, ',', '.') . ' €';

        //Calculo del % de pago 

        $totalInterest = ($cuotaMensual * $plazoEnMeses) - $amPrestamo;
        $capitalPaid = $amPrestamo - $totalInterest;
        $capitalPercentage1 = ($capitalPaid/$cuotaMensual)* 0.1;
        $capitalPercentage1 = round($capitalPercentage1, 2);
        $capitalPercentage1Formatted = $capitalPercentage1 . '%';

        $totalInterest2 = ($cuotaMensual2 * $plazoEnMeses) - $amPrestamo;
        $capitalPaid2 = $amPrestamo - $totalInterest2;
        $capitalPercentage2 = ($capitalPaid2/$cuotaMensual2)* 0.1;
        $capitalPercentage2 = round($capitalPercentage2, 2);
        $capitalPercentage1Formatted2 = $capitalPercentage2 . '%';

        $totalInterest3 = ($cuotaMensual3 * $plazoEnMeses) - $amPrestamo;
        $capitalPaid3 = $amPrestamo - $totalInterest3;
        $capitalPercentage3 = ($capitalPaid3/$cuotaMensual3)* 0.1;
        $capitalPercentage3 = round($capitalPercentage3, 2);
        $capitalPercentage1Formatted3 = $capitalPercentage3 . '%';

        //TIN %
        $mintinP= ($mintin*100);
        $mintinP2= ($mintin2*100);
        $mintinP3= ($mintin3*100);

        $taeP= ($tae*100);
        $taeP2= ($tae2*100);
        $taeP3= ($tae3*100);
        
        return view('dashboardLogin',compact('cuotaMensualFormateada','cuotaMensual','cuotaMensual2','cuotaMensual3',
        'banco','banco2','banco3','mintinP','mintinP2','mintinP3','capitalPercentage1','capitalPercentage2','capitalPercentage3',
        'taeP','taeP2','taeP3'));
    }

}
